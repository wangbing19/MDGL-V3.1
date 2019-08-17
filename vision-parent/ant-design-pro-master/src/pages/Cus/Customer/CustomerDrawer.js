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
        this.state = {  };
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
            if (!err) {
                const {customer: {  cusRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = cusRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                fieldsValue={
                    ...fieldsValue,
                    "birthday":fieldsValue['birthday'].format('YYYY/MM/DD'),
                }
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


    render() {
        const {form: {getFieldDecorator} ,customer: { drawerVisible, cusRow}, dispatch } = this.props;
        const { data, ok} = cusRow;
        return (
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >
                <Form  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} onSubmit={this.handleSubmit} >
                <Form.Item label='用户名' >
                        {getFieldDecorator('name', { rules: [{ ...rules.required  }],initialValue:ok?data["name"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='年龄' >
                        {getFieldDecorator('age', { rules: [{ ...rules.required  }],initialValue:ok?data["age"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='生日' >
                        {getFieldDecorator('birthday', { rules: [{ ...rules.required  }],initialValue:moment(ok?moment(data["birthday"]).format(dateFormat.day):moment().format(dateFormat.day), dateFormat.day_hour)
                        })(
                            <DatePicker format={dateFormat.day} style={{width:'100%'}}/>
                        )}
                    </Form.Item>
                    <Form.Item label='性别' >
                        {getFieldDecorator('gender', { rules: [{ ...rules.required  }],initialValue:ok?data["gender"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='家庭地址' >
                        {getFieldDecorator('home', { rules: [{ ...rules.required  }],initialValue:ok?data["home"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='学校地址' >
                        {getFieldDecorator('school', { rules: [{ ...rules.required  }],initialValue:ok?data["school"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='监护人' >
                        {getFieldDecorator('guardian', { rules: [{ ...rules.required  }],initialValue:ok?data["guardian"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='电话' >
                        {getFieldDecorator('tel', { rules: [{ ...rules.required  }],initialValue:ok?data["tel"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='次级监护人' >
                        {getFieldDecorator('lastGuardian', { initialValue:ok?data["lastGuardian"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='备用电话' >
                        {getFieldDecorator('lastGuardianTel', { initialValue:ok?data["lastGuardianTel"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='信息备注' >
                        {getFieldDecorator('remark', { initialValue:ok?data["remark"]:''
                        })(
                            <Input   />
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

export default CustomerDrawer;