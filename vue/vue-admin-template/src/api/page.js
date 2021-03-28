import request from '@/utils/request'

export function getList(url, params) {
  return request({
    url: url,
    method: 'get',
    params
  })
}
