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


@Form.create()
@connect(({remoteDiagnoseUser, loading }) => ({
    remoteDiagnoseUser,
    loading: loading.models.remoteDiagnoseUser,
}))
@connect(({remoteDiagnose, loading }) => ({
    remoteDiagnose,
    loading: loading.models.remoteDiagnose,
}))
class RemoteDiagnoseUserUpDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            expId:'',
         };
    }

   
   

    showDrawer=()=>{
        const {  remoteDiagnoseUser:{ drawerVisibleUpdate },dispatch } =this.props;
        dispatch({
            type:"remoteDiagnoseUser/setDrawerVisibleUpdate",
            payload:!drawerVisibleUpdate,
        })
        dispatch({
            type:'trainer/clearFeomData',
        })

        dispatch({
            type:"remoteDiagnoseUser/clearSysDiaRowOne",
           
        })
        //清空表单
        this.props.form.resetFields();
    }
    
    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {remoteDiagnoseUser: {  sysDiaRow, selectedRows,sysDiaRowOne }, dispatch } = this.props;
                const { data, ok} = sysDiaRowOne;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                console.log(`selected11 ${this.state.expId.value}`);
                console.log(this.state.expId);

                if(this.state.expId !=''){
                    formData.append("expertId",this.state.expId.value);
                }
                
               
                //发起请求
                formDataSubmit(dispatch,'remoteDiagnoseUser',formData);
                //清空选择的专家id
                this.setState(
                    this.state.expId='',
        
                 )

                
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


     handleChange=(value)=> {
         this.setState(
            this.state.expId={value},

         )
        console.log(`selected ${value}`);
       
      }
    render() {
        const {form: {getFieldDecorator} ,remoteDiagnoseUser: { sysDiaRowName,drawerVisibleUpdate, sysDiaRow,expertReplyRow,sysDiaRowOne}, dispatch } = this.props;
        const { data, ok} = sysDiaRowOne;
        
        return(
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisibleUpdate}
            width={'70%'}
            >
                   <Form  labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit} >
                   <div className={styles.titleSeting}>基础信息</div>
                     <br/><br/>
                     <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='顾客姓名' >
                            {getFieldDecorator('customerName', { rules: [{ ...rules.required  }],initialValue:ok?data["customerName"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='顾客电话' >
                            {getFieldDecorator('customerTel', { rules: [{ ...rules.required  }],initialValue:ok?data["customerTel"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='签字人' >
                            {getFieldDecorator('sendName', { rules: [{ ...rules.required  }],initialValue:ok?data["sendName"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='签字人电话' >
                            {getFieldDecorator('sendTel', { rules: [{ ...rules.required  }],initialValue:ok?data["sendTel"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>  


                 <hr/>
                <div className={styles.titleSeting}>订单信息</div>
                <br/><br/> 
                     <Form.Item label='门店名称'   >
                        {getFieldDecorator('deptName', { rules: [{ ...rules.required  }],initialValue:ok?data["deptName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='门店地址' >
                        {getFieldDecorator('deptSite', { rules: [{ ...rules.required  }],initialValue:ok?data["deptSite"] :''
                        })(
                            <Input   />
                        )}
                    </Form.Item>   
                    <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='选择专家' >
                            {getFieldDecorator('expertName', { rules: [{ ...rules.required  }],initialValue:ok?data["expertName"]:''
                            })(
                                <Select defaultValue="lucy" style={{ width: 120 }} onChange={this.handleChange}>
                                    <Option value="lucy">{data["expertName"]}</Option>
                                    {
                                        sysDiaRowName.data.map(function (key) {
                                            return  <Option value={key.id}>{key.name}</Option>
                                        })
                                    }
                                {/* <Option value="jack">Jack</Option>
                                <Option value="lucy">Lucy</Option>
                                <Option value="disabled" disabled>
                                  Disabled
                                </Option>
                                <Option value="Yiminghe">yiminghe</Option> */}
                              </Select>
                            )}
                        </Form.Item>
                    </Col>
                    </Row>
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

export default RemoteDiagnoseUserUpDrawer;