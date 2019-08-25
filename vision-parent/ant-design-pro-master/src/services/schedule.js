import request from '@/utils/request';
//查询
export async function getSchedule(params) {
    return request("/vision/cusSchedule/getSchedule",{
        method: "POST",
        body: params,
    });
}
//删除
export async function deleteSchedule(params) {
    return request("/vision/cusSchedule/deleteSchedule",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addSchedule(params) {
    return request("/vision/cusSchedule/addSchedule",{
        method: "POST",
        body: params,
    });
}
//修改
export async function updateSchedule(params) {
    return request("/vision/cusSchedule/updateSchedule",{
        method: "POST",
        body: params,
    });
}
//基于id查询
export async function getScheduleById(params) {
    return request("/vision/cusSchedule/getScheduleById",{
        method: "POST",
        body: params,
    });
}
//基于用户id查询课程信息
export async function getByCustomerId(params) {
    return request("/vision/cusSchedule/getByCustomerId",{
        method: "POST",
        body: params,
    });
}
