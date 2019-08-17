import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';

const { TextArea } = Input;
const Option = Select.Option;
const { MonthPicker, RangePicker } = DatePicker;

@Form.create()

@connect(({customer, loading }) => ({
    customer,
    loading: loading.models.customer,
}))


class CustomerDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            oculopathyOtherIsCan:false,
            fVisionConditionIsCan:false,
            mVisionConditionIsCan:false,
            eyeProjectOtherIsCan:false,
            eyePositionOtherIsCan:false,
         };
    }

    showDrawer=()=>{
        const {customer: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'customer/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'customer/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            debugger
            if (!err) {
                const {customer: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                fieldsValue = {
                    ...fieldsValue,
                    // "declineTimeYear":fieldsValue['declineTimeYear'].format('YYYY-MM'),
                }
                debugger
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1)
                //发起请求
                formDataSubmit(dispatch,'customer',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    //确认为何种病症
    oculopathyOtherIsCan=(e)=>{
        if(e.target.value=="4"){
            this.setState({
                oculopathyOtherIsCan:true,
            })
        } else if(e.target.value!="4"){
            this.setState({
                oculopathyOtherIsCan:false,
            })
            this.props.form.setFieldsValue({
                oculopathyOther: "",
            })
        }
        
    }
    //父亲:
    fVisionConditionIsCan=(e)=>{
        if(e.target.value=="11"){
            this.setState({
                fVisionConditionIsCan:true,
            })
        } else if(e.target.value!="11"){
            this.setState({
                fVisionConditionIsCan:false,
            })
        }
        
    }
    
    //母亲:
    mVisionConditionIsCan=(e)=>{
        if(e.target.value=="11"){
            this.setState({
                mVisionConditionIsCan:true,
            })
        } else if(e.target.value!="11"){
            this.setState({
                mVisionConditionIsCan:false,
            })
        }
    }

    setDisable=(data)=>{
        if(data["oculopathy"]=="4"){
            this.setState({
                oculopathyOtherIsCan:true,
            })
        }
        if(data["fvisionCondition"]=="11"){
            this.setState({
                fVisionConditionIsCan:true,
            })
        }
        if(data["mvisionCondition"]=="11"){
            this.setState({
                mVisionConditionIsCan:true,
            })
        }
    }

    //用眼项目
    eyeProjectOtherIsCan = value => {
        this.setState({
            eyeProjectOtherIsCan:false,
        })
        value.map((item) => {
            if(item=="3"){
                this.setState({
                    eyeProjectOtherIsCan:true,
                })
            }
        })
    }
    //用眼姿势
    eyePositionOtherIsCan = value => {
        this.setState({
            eyePositionOtherIsCan:false,
        })
        value.map((item) => {
            if(item=="6"){
                this.setState({
                    eyePositionOtherIsCan:true,
                })
            }
        })
    }


    render() {
        const { oculopathyOtherIsCan, fVisionConditionIsCan, mVisionConditionIsCan, eyeProjectOtherIsCan, eyePositionOtherIsCan  } = this.state;
        const {form: {getFieldDecorator} ,customer: { drawerVisible, cusRow}, dispatch } = this.props;
        const { data, ok} = cusRow;
        // if(ok){
        //     this.setDisable(data);
        // }
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'70%'}
            >
            <Form  labelCol={{ span: 8 }} wrapperCol={{ span: 15 }} onSubmit={this.handleSubmit} >
                <div className={styles.titleSeting}>基础信息</div>
                <br/><br/>
                <Row >
                    <Col md={6} sm={24}>
                        <Form.Item label='姓名' >
                            {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='年龄' >
                            {getFieldDecorator('age', { rules: [{ ...rules.required  }],initialValue:ok?data["age"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='性别' >
                            {getFieldDecorator('gender', { rules: [{ ...rules.required  }],initialValue:ok?data["gender"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='联系方式' >
                            {getFieldDecorator('tel', { rules: [{ ...rules.required  }],initialValue:ok?data["tel"]:''
                            })(
                                <Input   />
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
        );
    }
}

export default CustomerDrawer;