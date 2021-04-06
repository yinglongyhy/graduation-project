import request from '@/utils/request'

export function editPutById(url, params) {
  return request({
    url: url,
    method: 'put',
    params
  })
}
