import request from '@/utils/request';
//查询
export async function getSysLog(params) {
 
    return request("/vision/sysLogs/dofindLogs",{
        method: "POST",
        body: params,
    });
}