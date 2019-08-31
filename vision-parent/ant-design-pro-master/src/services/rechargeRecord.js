import request from '@/utils/request';
//查询
export async function getList(params) {
    return request("/vision/activityUser/findAllActivityRecords",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleted(params) {
    return request("/vision/activityUser/deleteActivityRecordByid",{
        method: "POST",
        body: params,
    });
}
//添加
export async function add(params) {
    return request("/vision/activityUser/insertActivityRecord",{
        method: "POST",
        body: params,
    });
}
// //修改
// export async function update(params) {
//     return request("/vision/activityUser/updateRecActivityById",{
//         method: "POST",
//         body: params,
//     });
// }
// //基于id查询
// export async function getById(params) {
//     return request("/vision/activityUser/findRecActivityObject",{
//         method: "POST",
//         body: params,
//     });
// }
