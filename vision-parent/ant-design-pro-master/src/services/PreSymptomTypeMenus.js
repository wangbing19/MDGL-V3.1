import request from '@/utils/request';
//查询
export async function getPreSymptomTypeAll(params) {
    return request("/vision/symptomType/findAllObjects",{
        method: "POST",
        body: params,
    });
}


//更据症状id查询处方信息
export async function findSymptomDesc(params) {

    return request("/vision/symptomDesc/findSymptomDescObjectByid",{
        method: "POST",
        body: params,
    });
}


//所有症状分页查询
export async function findAllObjectsList(params) {

    return request("/vision/symptomType/findAllObjectsList",{
        method: "POST",
        body: params,
    });
}

