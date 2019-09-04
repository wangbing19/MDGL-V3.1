import request from '@/utils/request';
//查询
export async function getPreSymptomTypeAll(params) {
    return request("/vision/sysMenu/doFindMenu",{
        method: "POST",
        body: params,
    });
}