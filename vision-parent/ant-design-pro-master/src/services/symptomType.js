import request from '@/utils/request';
//查询
export async function getSymptomTypeList(params) {
    return request("/vision/resSymptomType/getSymptomTypeList",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteSymptomType(params) {
    return request("/vision/resSymptomType/deleteSymptomType",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addSymptomType(params) {
    return request("/vision/resSymptomType/addSymptomType",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateSymptomType(params) {
    return request("/vision/resSymptomType/updateSymptomType",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getSymptomTypeById(params) {
    return request("/vision/resSymptomType/getSymptomTypeById",{
        method: "POST",
        body: params,
    });
}
//查询门店下所有资源配置
export async function getSymptomTypeListByOrgId(params) {
    return request("/vision/resSymptomType/getSymptomTypeListByOrgId",{
        method: "POST",
        body: params,
    });
}
//修改状态
export async function updateState(params) {
    return request("/vision/resSymptomType/updateState",{
        method: "POST",
        body: params,
    });
}
