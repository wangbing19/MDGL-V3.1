import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import StandardTable from '@/components/StandardTable/indexNatice';
import cookie from 'react-cookies';

const { TextArea } = Input;
const Option = Select.Option;
@Form.create()
@connect(({trainer, loading }) => ({
    trainer,
    loading: loading.models.trainer,
}))
    class TrainerTimeDrawer extends Component{
    constructor(props) {
        super(props);
        this.state = { 
            flag:1,
         };
    }

    showDrawer=()=>{
        const {trainer: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'trainer/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'trainer/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


     //表单验证成功后的回调函数
     handleSubmit = (e) => {
       
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
        
            if (!err) {
                const {trainer: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("organizationId",1)
                //发起请求
                formDataSubmit(dispatch,'trainer',formData);
              
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    onChange=(e)=>{
        this.setState({
            flag:e.target.value,
        })
    }

    render(){

        const { flag } = this.state;
        const {form: {getFieldDecorator} ,trainer: { drawerVisible, cusRow}, dispatch } = this.props;
        const DemoBox = props => <p className={`height-${props.value}`}>{props.children}</p>;
        const { TextArea } = Input;
        const { data, ok} = cusRow;
        return(

            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >
                <Form labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit}>
                    <div className={styles.titleSeting}>
                        训练师基本信息
                    </div>
                    <br/><br/>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='门店名' >
                            {getFieldDecorator('organizationName', { rules: [{ ...rules.required  }],initialValue:ok?data["organizationName"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    
                </Row>
                <Row>
                <Col md={6} sm={24}>
                        <Form.Item label='门店地址' >
                            {getFieldDecorator('organizationAddress', { rules: [{ ...rules.required  }],initialValue:ok?data["organizationAddress"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='姓名' >
                            {getFieldDecorator('trainerName', { rules: [{ ...rules.required  }],initialValue:ok?data["trainerName"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={4} sm={24}>
                        <Form.Item label='性别' >
                            {getFieldDecorator('trainerGender', { rules: [{ ...rules.required  }],initialValue:ok?data["trainerGender"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24}>
                        <Form.Item label='职位' >
                            {getFieldDecorator('post', { rules: [{ ...rules.required  }],initialValue:ok?data["post"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24}>
                        <Form.Item label='预约人数限制' >
                            {getFieldDecorator('numberPeople', { rules: [{ ...rules.required  }],initialValue:ok?data["numberPeople"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                   
                    <Col md={6} sm={24}>
                        <Form.Item label='联系人' >
                            {getFieldDecorator('linkman', { rules: [{ ...rules.required  }],initialValue:ok?data["linkman"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24}>
                        <Form.Item label='联系电话' >
                            {getFieldDecorator('phone', { rules: [{ ...rules.required  }],initialValue:ok?data["phone"]:''
                            })(
                                <Input   />
                              
                            )}
                        </Form.Item>
                    </Col>
                   

                </Row>
                <hr/>
                <Row>
                     <Col md={12} sm={24}>
                     <Form.Item label='简历' labelCol={{ span: 6 }}>
                            {getFieldDecorator('description', { rules: [ ],initialValue:ok?data["description"]:''
                            })(
                                <TextArea rows={6} />
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

export default TrainerTimeDrawer;

