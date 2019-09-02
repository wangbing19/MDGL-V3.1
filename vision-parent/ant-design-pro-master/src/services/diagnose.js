import request from '@/utils/request';
//查询
export async function getDiagnose(params) {
    return request("/vision/diagnose/getDiagnose",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteDiagnose(params) {
    return request("/vision/diagnose/deleteDiagnose",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addDiagnose(params) {
    return request("/vision/diagnose/addDiagnose",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateDiagnose(params) {
    return request("/vision/diagnose/updateDiagnose",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getDiagnoseById(params) {
    return request("/vision/diagnose/getDiagnoseById",{
        method: "POST",
        body: params,
    });
}
//基于CustomerId查询
export async function getByCustomerId(params) {
    return request("/vision/diagnose/getByCustomerId",{
        method: "POST",
        body: params,
    });
}