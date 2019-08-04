import { Modal, message } from 'antd';
//页面常用常量配置
export const systemConstant = {
    pageSize: 5, //分页条数
    deleteMsg: "确认删除吗?",
    success() { message.success("操作成功") },
    deleteSuccess() {message.success("删除成功")},
    editSuccess() {message.success("修改成功")},
    addSuccess() {message.success("添加成功")},
    error(content) {
        Modal.error({
            title: '操作失败',
            content: content,
        });
    },
    errorType(type,content) {
        Modal.error({
            title: type+'失败',
            content: content,
        });
    },
    // SSOTOKEN:'MDEeaOdGnqg87yqsS3ESGMwWlzMj40LRIlT2E2WQuZWqmalpJZo/fTem',
    sysId:sessionStorage.getItem("sysId"),
    sysCode:sessionStorage.getItem("sysCode"),
};
export const cookiesConfig = {
    maxAge:new Date().setDate(new Date().getDate()+30),
    path:'/',
};
export const dateFormat = {
    hour:'HH:mm:ss',
    day:'YYYY-MM-DD',
    day_hour:'YYYY-MM-DD HH:mm:ss',
}
//返回值状态编码
export const responseCode = {
    SUCCESS: "000000", //成功
    FAILED: "999999",//失败
};
//表单验证常用属性
export const rules = {
    required: { required: true, message: '不能为空' },
    whitespace:{ required: true, message: '不能为空且不为空格',whitespace:true },//必选时，空格是否会被视为错误，默认为false
    notRequired: { required: false},
    maxLength(max) {
        return { max: max, message: `不能大于${max}个字符` }
    },
    minLength(min) {
        return { min: min, message: `不能小于${min}个字符` }
    },
    email: { type: 'email', message: '请输入有效的电子邮件' },
    number: { type: 'number', message: '请输入正确的数字' },
    boolean: { type: 'boolean', message: '请输入true或者false' },
    integer: { type: 'integer', message: '只能输入整数' },
    float: { type: 'float', message: '只能输入浮点数' },
    date: { type: 'object', message: '请输入有效的日期' },
    url: { type: 'url', message: '请输入有效的网址' },
    code: { pattern: /^[a-zA-Z_-][a-zA-Z0-9_-]*$/, message: '只能由字母、数字、下划线、中划线组成，且不能以数字开头' },
    special: { pattern: /^([^<>])*$/, message: '不能包含特殊字符' },
    positiveNumber: { pattern: /^\d+(\.{0,1}\d+){0,1}$/, message: '只能输入非负数' },
    threeDecimal:{pattern:/^[+-]?\d*\.?\d{1,3}$/,message: '小数位数最多三位'},
    twoDecimal:{pattern:/^[+-]?\d*\.?\d{1,2}$/,message: '小数位数最多两位'},
    name:{pattern: /^[a-zA-Z_][a-zA-Z0-9_]{0,200}$/, message: '只能由汉字、字母、数字、下划线组成，且不能以数字开头'},//汉字未写出
    mobileNo:{pattern: /^[1][0-9]{10}$/, message: '手机号码由数字1开头的11位数字组成'},//手机号
    shortNo:{pattern: /^\d{6}$/, message: '集团短号由6位数字组成'},//集团短号
    special2:{pattern: /^[\w.\-\u4e00-\u9fa5]+$/, message: '不能包含特殊字符'},
    password:{pattern: /^[^\u4e00-\u9fa5]{0,}$/, message: '密码中不能包含中文'},
    
    
    
}

export const regex = {
    email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    number: /^[0-9]{20,30}$/,//纯数字
    date: /^\d{4}-\d{1,2}-\d{1,2}/,//日期
    code: /^[a-zA-Z_-][a-zA-Z0-9_-]{40}$/, //只能由字母、数字、下划线、中划线组成，且不能以数字开头
    special:  /^([^<>])*$/,//不能包含特殊字符
    positiveNumber: /^\d+(\.{0,1}\d+){0,1}$/, //只能输入非负数
    threeDecimal:/^[+-]?\d*\.?\d{1,3}$/,//小数位数最多三位
    twoDecimal:/^[+-]?\d*\.?\d{1,2}$/,//小数位数最多两位
    phone: /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/,//手机号
    userId:/^([0-9]){17}([0-9]|x)$/,//身份证
    Chinese:/^[\u4E00-\u9FA5]{2,10}$/,//汉字
}

// //配置服务地址
// export const greenfactory = "/greenfactory";
// //配置文件中心服务地址
// export const wikinfile = "/zuul/wikin-file";

//开发的时候修改为此配置
//开发使用显示左侧菜单树，发布的时候换一个没有菜单的
// export const  baselayout =() => import('./src/layouts/BasicLayout');
//开发中使用反向代理请求后台，所以需要权限认证，需要有效的jwtToken
// export const userHeaders = {
//     jwtToken: "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbmlzdHJhdG9yIiwiY3JlYXRlZCI6MTU0NzQzNTI4Mzg4OSwiZXhwIjoxNTQ3NDQ5NjgzLCJ0ZW5hbnQiOiJEUUdCIiwidXNlclZvIjoie1wiZGVwYXJ0bWVudE5hbWVcIjpcIuWkp-W6humHkeahpVwiLFwidGVhbU5hbWVcIjpcIlwiLFwiYWRkcmVzc1wiOlwiXCIsXCJkZXBhcnRtZW50SWRcIjpcIjBmN2YyNTdmYjllNTQ5ZjZiNDUyMmJmMDRhMzAwNWI4XCIsXCJpcFwiOlwiXCIsXCJ0ZWxlcGhvbmVcIjpcIlwiLFwidXNlck5hbWVcIjpcImFkbWluaXN0cmF0b3JcIixcInVzZXJJZFwiOlwiZGMzYzMxNzM3ZWQ1NDQ2MmE1NDQyMjI5MTc1MzNmODZcIixcInJlYWxOYW1lXCI6XCLlpKfluobph5HmoaXnrqHnkIblkZhcIixcInBhc3N3b3JkXCI6XCJcIixcInRlYW1JZFwiOlwiXCIsXCJlcnBJZFwiOlwiXCIsXCJyZmlkXCI6XCJcIixcImlzUmVnaW9udXNlclwiOjAsXCJlbWFpbFwiOlwiMjAyNDQwNzY2MUBxcS5jb21cIixcInRlbmFudFwiOlwiRFFHQlwifSJ9.WZ-19VUPiUzYP5ULn_Ax2AksZiFpVfQSGmBOkcevr0L5mDOqufMfp3gJE_p_XPHK-gtt84OyJUWYg8srFboFVA"
// };

//发布的时候修改为此配置
//配置服务地址
// export const userHeaders = {
// };
// export const  baselayout =() => import('./src/layouts/UrlLayout');
