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

@connect(({schedule, loading }) => ({
    schedule,
    loading: loading.models.schedule,
}))


class ScheduleDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=()=>{
        const {schedule: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'schedule/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'schedule/clearFeomData',
        })
        const data = {
            records:[],
        }
        dispatch({
            type:'schedule/saveSymptomTypesList',
            payload:{
                data
            },
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {schedule: {  scheduleRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = scheduleRow;
                fieldsValue={
                    ...fieldsValue,
                    "gmtModified":fieldsValue['gmtModified'].format('YYYY/MM/DD HH:mm:ss'),
                }
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1);
                //发起请求
                formDataSubmit(dispatch,'schedule',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    
    ChangeNumberOfCourse=(value)=>{
        this.props.form.setFieldsValue({
            numberOfCourse: value.length,
        })
    }

    dogettotalPriceId=(value)=>{
        var quantity = this.props.form.getFieldsValue().courseTraining;
        if(quantity==0){
            return;
        }
        this.props.form.setFieldsValue({
            totalPrice: quantity*value,
        })

    }

    calculateTotalPriceId=(value)=>{
        var unitPrice = this.props.form.getFieldsValue().priceOfCourse;
        if(unitPrice==0){
            return;
        }
        this.props.form.setFieldsValue({
            totalPrice: unitPrice*value,
        })
    }

    render() {
        const {form: {getFieldDecorator} ,schedule: { drawerVisible, scheduleRow, symptomTypesList, customer}, dispatch } = this.props;
        const { data, ok} = scheduleRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 6 }} wrapperCol={{ span: 17 }} onSubmit={this.handleSubmit} >
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('customerId', { rules: [{ ...rules.required  }],initialValue:ok?data["customerId"]:customer["id"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='姓名' >
                        {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:customer["name"]
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='课程名称' >
                        {getFieldDecorator('courseTitle', { rules: [{ ...rules.required  }],initialValue:ok?data["courseTitle"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='课程价格' >
                        {getFieldDecorator('priceOfCourse', { rules: [{ ...rules.required  }],initialValue:ok?data["priceOfCourse"]:''
                        })(
                            <InputNumber  onChange={this.dogettotalPriceId} style={{width:"100%"}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='课程建议训练数' >
                        {getFieldDecorator('courseTraining', { rules: [{ ...rules.required  }],initialValue:ok?data["courseTraining"]:''
                        })(
                            <InputNumber  onChange={this.calculateTotalPriceId}  style={{width:"100%"}} />
                        )}
                    </Form.Item>
                    <Form.Item label='课程项目数' >
                        {getFieldDecorator('numberOfCourse', { rules: [{ ...rules.required  }],initialValue:ok?data["numberOfCourse"]:''
                        })(
                            <Input  disabled={true} />
                        )}
                    </Form.Item>
                    <Form.Item label='建议训练数总价格' >
                        {getFieldDecorator('totalPrice', { rules: [{ ...rules.required  }],initialValue:ok?data["totalPrice"]:''
                        })(
                            <InputNumber   style={{width:"100%"}} />
                        )}
                    </Form.Item>
                    <Form.Item label='训练项目' >
                        {getFieldDecorator('symptomTypes', { rules: [{ ...rules.required  }],initialValue:ok?data["symptomTypes"]:''
                        })(
                            <Checkbox.Group onChange={this.ChangeNumberOfCourse}>
                            {
                                symptomTypesList.map((value,key)=>{
                                    return (
                                        <Checkbox value={value.id}>{value.title}</Checkbox>
                                    );
                                })
                            }
                            </Checkbox.Group>
                        )}
                    </Form.Item>
                    <Form.Item label='诊断师' >
                        {getFieldDecorator('diagnostics', { rules: [{ ...rules.required  }],initialValue:ok?data["diagnostics"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='时间' >
                        {getFieldDecorator('gmtModified', { rules: [{ ...rules.required  }],initialValue:moment(ok?moment(data["gmtModified"]).format(dateFormat.day_hour):moment().format(dateFormat.day_hour), dateFormat.day_hour)
                        })(
                            <DatePicker format={dateFormat.day_hour} style={{width:'100%'}}/>
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
        );
    }
}

export default ScheduleDrawer;