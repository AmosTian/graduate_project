import { routerRedux } from 'dva/router';
import { message } from 'antd';
import {addHouseResource, queryResource, updateHouseResource} from '@/services/haoke/haoke';

export default {
  namespace: 'house',

  state: {
    data: {
      list: [],
      pagination: {},
    },
  },

  effects: {
    *submitHouseForm({ payload }, { call }) {
      console.log("page model")
      yield call(addHouseResource, payload);
      message.success('提交成功');
    },
    *updateHouseForm({ payload }, { call }) {
      yield call(updateHouseResource, payload);
      message.success('提交成功');
    },
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryResource, payload);
      yield put({
        type: 'save',
        payload: response,
      });
    }
  },

  reducers: {
    save(state, action) {// state表示当前model的数据，action表示异步函数 put ，put()中的payload为封装了回调数据的属性
      return {
        ...state,
        data: action.payload,
      };
    },
  }
};
