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

@connect(({traInformationrecord, loading }) => ({
    traInformationrecord,
    loading: loading.models.traInformationrecord,
}))


class TraInformationrecordDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    showDrawer=()=>{
        const {traInformationrecord: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'traInformationrecord/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'traInformationrecord/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {traInformationrecord: {  traRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = traRow;
                fieldsValue={
                    ...fieldsValue,
                    "endTime":fieldsValue['endTime'].format('YYYY/MM/DD HH:mm:ss'),
                }
                const formData = formatData(fieldsValue,"",data["id"]);
                //发起请求
                formDataSubmit(dispatch,'traInformationrecord',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }


    render() {
        const {form: {getFieldDecorator} ,traInformationrecord: { drawerVisible, traRow}, dispatch } = this.props;
        const { data, ok} = traRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 5 }} wrapperCol={{ span: 18 }} onSubmit={this.handleSubmit} >
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('customerId', { rules: [{ ...rules.required  }],initialValue:ok?data["customerId"]:''
                        })(
                            <Input />
                        )}
                    </Form.Item>
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('scheduleId', { rules: [{ ...rules.required  }],initialValue:ok?data["scheduleId"]:''
                        })(
                            <Input />
                        )}
                    </Form.Item>
                    <Form.Item style={{display:"none"}} >
                        {getFieldDecorator('orgId', { rules: [{ ...rules.required  }],initialValue:ok?data["orgId"]:1
                        })(
                            <Input />
                        )}
                    </Form.Item>
                    <Form.Item label='姓名' >
                        {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='裸眼视力(左)' >
                        {getFieldDecorator('lVision', { rules: [{ ...rules.required  }],initialValue:ok?data["lvision"]:''
                        })(
                            <InputNumber style={{width:"100%"}}   />
                        )}
                    </Form.Item>
                    <Form.Item label='裸眼视力(右)' >
                        {getFieldDecorator('rVision', { rules: [{ ...rules.required  }],initialValue:ok?data["rvision"]:''
                        })(
                            <InputNumber style={{width:"100%"}}   />
                        )}
                    </Form.Item>
                    <Form.Item label='评分' >
                        {getFieldDecorator('grade', { rules: [{ ...rules.required  }],initialValue:ok?data["grade"]:''
                        })(
                            <InputNumber style={{width:"100%"}}   />
                        )}
                    </Form.Item>
                    <Form.Item label='评价' >
                        {getFieldDecorator('evaluate', { rules: [{ ...rules.required  }],initialValue:ok?data["evaluate"]:''
                        })(
                            <Input.TextArea rows={4}/>
                        )}
                    </Form.Item>
                    <Form.Item label='项目内容' >
                        {getFieldDecorator('content', { rules: [{ ...rules.required  }],initialValue:ok?data["content"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='训练师' >
                        {getFieldDecorator('tutor', { rules: [{ ...rules.required  }],initialValue:ok?data["tutor"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='时间' >
                        {getFieldDecorator('endTime', { rules: [{ ...rules.required  }],initialValue:moment(ok?moment(data["endTime"]).format(dateFormat.day_hour):moment().format(dateFormat.day_hour), dateFormat.day_hour)
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

export default TraInformationrecordDrawer;