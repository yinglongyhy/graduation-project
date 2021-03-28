import request from '@/utils/request'

export function editById(url, params) {
  return request({
    url: url,
    method: 'post',
    params
  })
}
