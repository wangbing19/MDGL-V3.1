import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker,AutoComplete  } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';


const { TextArea } = Input;
const Option = Select.Option;
const { MonthPicker, RangePicker } = DatePicker;

//const plainOptions = ['Apple', 'Pear', 'Orange','小王'];
//    const plainOptions = [   {  value: 1,label: 'Apple' },
//    { label: 'Pear', value: 2 },
//     { label: 'Orange', value: 3 },];

// const defaultCheckedList = [ { label: 'Apple', value: 'Apple' },
// { label: 'Pear', value: 'Pear' },];
// const defaultCheckedList = [1, 3];

@Form.create()
@connect(({sysUser, loading }) => ({
    sysUser,
    loading: loading.models.sysUser,
}))
class SysUserDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {
          
            roleIs:[] ,
            result: [],
            checkedList:[],
            indeterminate: true,
             checkAll: false,
        };
        
    };


    handleSearch = value => {
        let result;
        if (!value || value.indexOf('@') >= 0) {
          result = [];
        } else {
          result = ['gmail.com', '163.com', 'qq.com'].map(domain => `${value}@${domain}`);
        }
        this.setState({ result });
      };
    
    showDrawer=()=>{
        const {sysUser: { drawerVisible }, dispatch} = this.props;
     
        dispatch({
            type:'sysUser/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'sysUser/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }

       //表单验证成功后的回调函数
       handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {sysUser: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("roleIds",this.state.roleIs);
                formData.append("organizationId",1);
                //发起请求
                formDataSubmit(dispatch,'sysUser',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    };

    //到后台查询角色信息
    getcquireRole =(startValue )=>{
       
        const {  sysUser:{ cquireRole },dispatch } =this.props;
       
             dispatch({
                 type:"sysUser/setCquireRole",
                 payload:!cquireRole,
             })
        if(startValue){
            dispatch({
                type:'sysUser/getRole',
                
            })
        }

    };
    //存储角色id
    setcquireRole =value=>{
        this.setState(
           this.state.roleIs=value
        )
      
    };
    //多选框点击事件
    onChange = checkedList => {
     
        this.setState({
          checkedList,
          indeterminate: !!checkedList.length && checkedList.length < plainOptions.length,
          checkAll: checkedList.length === plainOptions.length,
        });
      };
    //    //多选框点击事件
    //   onCheckAllChange = e => {
    //     console.log(e);
    //       debugger
       
    //     this.setState({
    //       checkedList: e.target.checked ? plainOptions : [],
    //       indeterminate: false,
    //       checkAll: e.target.checked,
    //     });
    //   };

    ChangeNumberOfCourse=(value)=>{
        this.props.form.setFieldsValue({
            numberOfCourse: value.length,
        })
    }
    
    render() {
        const {form: {getFieldDecorator} ,sysUser: { drawerVisible, cusRow,cquireRole,defaultCheckedList,plainOptions}, dispatch } = this.props;
        const { data, ok} = cusRow;
        const { result } = this.state;
        const children = result.map(email => <Option key={email}>{email}</Option>);
        const plainOptionsData = [plainOptions];
       // this.setData(defaultCheckedList);
      //  console.log(this.checkedList);

        return (
          
                 <Drawer 
                    title={ok?"修改":"添加"}
                    placement="right"
                    closable={false}
                    onClose={this.showDrawer}
                    visible={drawerVisible}
                    width={'40%'}>
                     <Form  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} onSubmit={this.handleSubmit} >


                    
                     <Form.Item label='门店名称' >
                        {getFieldDecorator('organizationName', { rules: [{ ...rules.required  }],initialValue:ok?data["organizationName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='门店地址' >
                        {getFieldDecorator('organizationAddress', { rules: [{ ...rules.required  }],initialValue:ok?data["organizationAddress"] :''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    <Form.Item label='用户名' >
                        {getFieldDecorator('userName', { rules: [{ ...rules.required  }],initialValue:ok?data["userName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='邮件' >
                        {getFieldDecorator('email', { rules: [{ ...rules.required  }],initialValue:ok?data["email"]:''
                        })(
                            //<Input   />
                            <AutoComplete style={{ width: 200 }} onSearch={this.handleSearch} placeholder="输入邮箱地址">
                                 {children}
                                 </AutoComplete>
                        )}
                    </Form.Item>
                    <Form.Item label='手机号' >
                        {getFieldDecorator('mobile', { rules: [{ ...rules.required  }],initialValue:ok?data["mobile"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>

                    
                    <Form.Item label='备注' >
                        {getFieldDecorator('remark', { rules: [{ ...rules.recnpmquired  }],initialValue:ok?data["remark"]:''
                        })(
                            <Input   />
                        )}
                        </Form.Item>
                    <Row >
                    <Col md={18} sm={14}>
                        <Form.Item label='顾客上限' >
                            {getFieldDecorator('customerLimit', { rules: [{ ...rules.required  }],initialValue:ok?data["customerLimit"]:''
                            })(
                                <Input />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={18} sm={14}>
                        <Form.Item label='门店上限' >
                            {getFieldDecorator('deptLimit', { rules: [{ ...rules.required  }],initialValue:ok?data["deptLimit"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>用户角色设置：</div>
                <br/><br/>

                <Form.Item label='' >
                    {getFieldDecorator('correctionMethod', { rules: [{ ...rules.required  }],initialValue:ok?data["correctionMethod"]:''
                    })
                    
                    // (
                    //     <Checkbox.Group onChange={cquireRole?this.getcquireRole(cquireRole):this.setcquireRole}>                        
                    //            {
                    //                 cquireRole?'':RoleRow.data.map(function (value) {
                    //                     return <Checkbox value={value.id}>{value.name}</Checkbox>
                    //                 })
                    //             }                                                        
                    //         {/* <Checkbox value={cquireRole?'':RoleRow.data[0].id}>{cquireRole?'':RoleRow.data[0].name}</Checkbox>
                    //         <Checkbox value="1">店长</Checkbox>
                    //         <Checkbox value="2">店员</Checkbox> */}
                    //     </Checkbox.Group>
                    // )
                    
                    (

                  
                            //     <div>
                            // {/* <div style={{ borderBottom: '1px solid #E9E9E9' }}>
                            //    <Checkbox
                            //      indeterminate={this.state.indeterminate}
                            //      onChange={this.onCheckAllChange}
                            //      checked={this.state.checkAll}
                            //    >
                            //      Check all
                            //    </Checkbox>
                            //  </div>
                            //  <br /> */}
                            //  <CheckboxGroup
                            //    options={plainOptions}
                            //    value={defaultCheckedList}
                            //    onChange={this.onChange}
                            //  />
                            //    </div>
                        
                            <Checkbox.Group >
                            {
                                plainOptionsData.map((result,key)=>{
                                  
                                    return (
                                        <Checkbox value={result.id}>{result.title}</Checkbox>
                                    );
                                })
                            }
                            </Checkbox.Group>

                    )
                    }
                </Form.Item>

                <Form.Item label='是否生效' >
                        {getFieldDecorator('userStatus', { rules: [{ ...rules.required  }],initialValue:ok?data["userStatus"]:''
                        })(
                            <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>
                 
                    <div className={styles.buttons} >
                        <Button onClick={this.showDrawer} className={styles.cancel} >
                            返回
                        </Button>
                        <Button  type="primary" htmlType="submit">
                            提交
                        </Button>
                    </div>
                </Form>


                 </Drawer>
               
           
        )
    }
}
export default SysUserDrawer;