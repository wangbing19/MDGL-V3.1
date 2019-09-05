import React, { Component } from 'react';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Checkbox, DatePicker } from 'antd';
import { connect } from 'dva';
import styles from '@/less/config.less';
import {rules,dateFormat} from '../../../../config/utilsConfig';
import {formDataSubmit,formatData} from '@/utils/dataUtils';
import cookie from 'react-cookies';
import moment from 'moment';
import BaseDicTree from '@/components/BaseTree/BaseDicTree';
const { TextArea } = Input;
const Option = Select.Option;
const { MonthPicker, RangePicker } = DatePicker;
@Form.create()
@connect(({preSymptomType, loading }) => ({
    preSymptomType,
    loading: loading.models.preSymptomType,
}))
class PreSymptomTypeDrawer extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            symptomTypePid:''
         };
    }


    showDrawer=()=>{
        const {preSymptomType: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'preSymptomType/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'preSymptomType/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }

       //表单验证成功后的回调函数
       handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {preSymptomType: {  DescRowById, selectedRows }, dispatch } = this.props;
                const { data, ok} = DescRowById;
               
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                if(!ok){
                    formData.append("parentId", this.state.symptomTypePid);
                }
                //发起请求
                formDataSubmit(dispatch,'preSymptomType',formData);
                //关闭抽屉
                this.setState(
                    this.state.symptomTypePid='',
                );
                this.showDrawer();
            }
        });
    }

    onSelect=(e)=>{

      this.setState(
          this.state.symptomTypePid=e,
      )
    
    
    }
    render() {
        const {form: {getFieldDecorator} ,preSymptomType: { drawerVisible, savRow,treeList,DescRowById}, dispatch } = this.props;
        const { data, ok} = DescRowById;
        const treeNodeLists = [treeList]
        return(
            <Drawer
            title={ok?"修改":"添加"}
            placement="right"
            closable={false}
            onClose={this.showDrawer}
            visible={drawerVisible}
            width={'40%'}
            >

                <Form  labelCol={{ span: 4 }} wrapperCol={{ span: 19 }} onSubmit={this.handleSubmit} >
                    <Form.Item label='症状名称' >
                        {getFieldDecorator('symptomName', { rules: [{ ...rules.required  }],initialValue:ok?data["symptomName"]:''
                        })(
                            <Input   />
                        )}
                    </Form.Item>
                   
                    <Form.Item label='是否有添加处方' >
                        {getFieldDecorator('descStart', { rules: [{ ...rules.required  }],initialValue:ok?data["descStart"]:''
                        })(
                            <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                <Radio value={1}>是</Radio>
                                <Radio value={0}>否</Radio>
                            </Radio.Group>
                        )}
                    </Form.Item>

                    <Form.Item label='处方信息' >
                        {getFieldDecorator('desc', { rules: [{ ...rules.required  }],initialValue:ok?data["desc"]:''
                        })(
                            <Input.TextArea rows={4}   />
                        )}
                    </Form.Item>

                    <p> 选择上级症状类型</p>
                        
                    <BaseDicTree
                            filterTreeNode="true"
                            onSelect={this.onSelect}
                            treeList={treeNodeLists}
                            defaultExpandedKeys={[treeNodeLists[0].id]}
                     />

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
export default PreSymptomTypeDrawer;