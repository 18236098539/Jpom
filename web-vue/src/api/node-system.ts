import axios from './config'

/**
 * white list data
 * @param {nodeId} nodeId
 */
export function getWhiteList(data) {
  return axios({
    url: '/node/system/white-list',
    method: 'post',
    data: data
  })
}

/**
 * edit white list data
 * @param {
 *  nodeId: 节点 ID,
 *  project: 项目目录,


 * } params
 */
export function editWhiteList(params) {
  return axios({
    url: '/node/system/whitelistDirectory_submit',
    method: 'post',
    data: params
  })
}
