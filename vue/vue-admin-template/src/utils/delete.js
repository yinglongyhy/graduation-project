import request from '@/utils/request'

export function deleteById(url, params) {
  return request({
    url: url,
    method: 'delete',
    params
  })
}
