import request from '@/utils/request';
import {stringify} from "qs";

export async function addHouseResource(params) {
  return request('/haoke/house/resources', {
    method: 'POST',
    body: params
  });
}

export async function updateHouseResource(params) {
  console.log(params)
  return request('/haoke/house/resources', {
    method: 'PUT',
    body: params
  });
}

export async function queryResource(params) {
  return request(`/haoke/house/resources/list?${stringify(params)}`);
}
