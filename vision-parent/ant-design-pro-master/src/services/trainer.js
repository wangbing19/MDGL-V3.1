import request from '@/utils/request';
//查询
export async function getTrainer(params) {
    return request("/vision/PpoTrainer/doFindPpoTrainer",{
        method: "POST",
        body: params,
    });
}
//添加
export async function addTrainer(params) {
    return request("/vision/PpoTrainer/doInsertPpoTrainer",{
        method: "POST",
        body: params,
    });
}
//基于id查询信息
export async function getTrainerById(params) {
  
    return request("/vision/PpoTrainer/doFindPpoTrainerOne",{
        method: "POST",
        body: params,
    });
}
//基于id删除
export async function deleteTrainer(params) {

    return request("/vision/PpoTrainer/dodeletePpoTrainer",{
        method: "POST",
        body: params,
    });
}
//添加
export async function updateTrainer(params) {
    return request("/vision/PpoTrainer/doUpdatePpoTrainer",{
        method: "POST",
        body: params,
    });
}


