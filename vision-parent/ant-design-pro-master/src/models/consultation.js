import { getConsultation } from '@/services/consultation';
import {formatData, FormdateFormat} from '@/utils/dataUtils';

export default {
    namespace: 'consultation',

    state: {
        data:{
            list:[],
        },
    },

    effects: {
        //分页查询
        *fetch( { payload }, { select, call, put }) {
            //从cookie获取limit的值，无值从utilsConfig获取
            payload ={
                ...payload,
            }
            const formData = formatData(payload);
            const response = yield call(getConsultation,formData);
            yield put({
                type: 'save',
                payload: {
                    ...response,
                    ...payload,
                },
            });
        },
    },

    reducers: {
        save(state, action) {
            return {
                ...state,
                data:{
                    ...state.data,
                    list:action.payload.data.records,
                    ...action.payload,
                },
            };
        },
    },

};