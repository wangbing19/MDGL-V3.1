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
@connect(({ppoServiceItems, loading }) => ({
    ppoServiceItems,
    loading: loading.models.ppoServiceItems,
}))
class PpoServiceItemsDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { };
    }

    showDrawer=()=>{
        const {ppoServiceItems: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'ppoServiceItems/setDrawerVisible',
            payload:!drawerVisible,
        });
        dispatch({
            type:'ppoServiceItems/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    };
    
    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {ppoServiceItems: {  serviceItemRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = serviceItemRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("organizationId",1);
                //发起请求
                formDataSubmit(dispatch,'ppoServiceItems',formData);
                //关闭抽屉
                this.showDrawer();
            }
        });
    }

    render() {
        const {form: {getFieldDecorator} ,ppoServiceItems: { drawerVisible, serviceItemRow}, dispatch } = this.props;
        const { data, ok} = serviceItemRow;

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
                    <Form.Item label='服务名称' >
                        {getFieldDecorator('serviceName', { rules: [{ ...rules.required  }],initialValue:ok?data["serviceName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                    <Form.Item label='服务描述' >
                        {getFieldDecorator('serviceContent', { rules: [{ ...rules.required  }],initialValue:ok?data["serviceContent"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>

                    <Form.Item label='是否生效' >
                        {getFieldDecorator('serviceState', { rules: [{ ...rules.required  }],initialValue:ok?data["serviceState"]:''
                        })(
                            <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>

                    <Form.Item label='备注' >
                        {getFieldDecorator('serviceRemark', { rules: [{ ...rules.required  }],initialValue:ok?data["serviceRemark"]:''
                        })(
                            <Input.TextArea rows={4}   />
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
export default PpoServiceItemsDrawer;