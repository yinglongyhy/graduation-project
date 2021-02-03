# 把 'https://github.com/modood/Administrative-divisions-of-China' 里的地址数据(csv)导入到数据库的address表中

import csv

import pymysql


class AddressDataImport:

    def __init__(self):
        host = 'localhost'
        port = 3307
        db = 'fang'
        user = 'root'
        password = 'root'
        self.conn = pymysql.connect(host=host, port=port, db=db, user=user, password=password, charset='utf8mb4')
        self.cur = self.conn.cursor()

    def __del__(self):
        self.cur.close()
        self.conn.close()

    def insert(self, code, name, fullname):
        sql = 'insert into address(code, name, fullname) values(%s, %s, %s)'
        self.cur.execute(sql, (code, name, fullname))

    def get_address_from_csv(self, filename):
        with open('./data/' + filename + '.csv', 'r') as f:
            reader = csv.reader(f)
            for item in reader:
                if reader.line_num == 1:
                    continue
                yield item[0], item[1]

    def get_fullname(self, code: str, name: str):
        sql = 'select fullname from address where code = %s'
        if len(code) == 2:
            return name
        elif len(code) == 4 or len(code) == 6:
            code = code[:-2]
        else:
            code = code[:-3]
        self.cur.execute(sql, (code,))
        fullname = self.cur.fetchone()
        fullname = fullname[0] + name
        return fullname

    def address_import(self, files: list):
        try:
            for filename in files:
                for code, name in self.get_address_from_csv(filename):
                    fullname = self.get_fullname(code, name)
                    self.insert(code, name, fullname)
                    print(code, name, fullname)
            self.conn.commit()
        except BaseException as e:
            print(e)
            self.conn.rollback()


if __name__ == '__main__':
    files = ['provinces', 'cities', 'areas', 'streets', 'villages']
    adi = AddressDataImport()
    adi.address_import(files)