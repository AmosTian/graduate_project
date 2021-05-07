import { queryResource } from '@/services/haoke/haoke';

export default {
  namespace: 'houseResource',

  state: {
    data: {
      list: [],
      pagination: {},
    },
  },

  effects: {
    *fetch({ payload }, { call, put }) {
      const response = yield call(queryResource, payload);
      console.log(response)
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
  },
};
