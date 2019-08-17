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

@connect(({consultation, loading }) => ({
    consultation,
    loading: loading.models.consultation,
}))


class ConsultationDrawer extends Component {
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
        const {consultation: { drawerVisible }, dispatch} = this.props;
        dispatch({
            type:'consultation/setDrawerVisible',
            payload:!drawerVisible,
        })
        dispatch({
            type:'consultation/clearFeomData',
        })
        //清空表单
        this.props.form.resetFields();
    }


    //表单验证成功后的回调函数
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, fieldsValue) => {
            if (!err) {
                const {consultation: {  conRow, selectedRows }, dispatch } = this.props;
                const { data, ok} = conRow;
                const formData = formatData(fieldsValue,"",data["id"]);
                //封装表单数据对象
                // const formData = formatData(fieldsValue);
                formData.append("orgId",1)
                //发起请求
                formDataSubmit(dispatch,'consultation',formData);
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
        const {form: {getFieldDecorator} ,consultation: { drawerVisible, conRow}, dispatch } = this.props;
        const { data, ok} = conRow;
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
                <hr/>
                <div className={styles.titleSeting}>主要信息采集</div>
                <br/><br/>
                <span>1.您从什么时候发现视力下降</span>
                <Row >
                    <Col md={8} sm={24}>
                        <Form.Item label='左右眼' >
                            {getFieldDecorator('eye', { rules: [{ ...rules.required  }],initialValue:ok?data["eye"]:''
                            })(
                                <Input   />
                                // <Select defaultValue="左眼" style={{ width: 120 }} >
                                //     <Option value="左眼">左眼</Option>
                                //     <Option value="右眼">右眼</Option>
                                //     <Option value="双眼">双眼</Option>
                                // </Select>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='年份' >
                            {getFieldDecorator('declineTimeYear', { rules: [{ ...rules.required  }],initialValue:ok?data["declineTimeYear"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='月份' >
                            {getFieldDecorator('declineTimeMonth', { rules: [{ ...rules.required  }],initialValue:ok?data["declineTimeMonth"]:''
                            })(
                                <Input   />
                                // <Select defaultValue="1" style={{ width: 120 }} >
                                //     <Option value="1">1月</Option>
                                //     <Option value="2">2月</Option>
                                //     <Option value="3">3月</Option>
                                //     <Option value="4">4月</Option>
                                //     <Option value="5">5月</Option>
                                //     <Option value="6">6月</Option>
                                //     <Option value="7">7月</Option>
                                //     <Option value="8">8月</Option>
                                //     <Option value="9">9月</Option>
                                //     <Option value="10">10月</Option>
                                //     <Option value="11">11月</Option>
                                //     <Option value="12">12月</Option>
                                // </Select>
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>2.是否到专业医疗机构确诊</span>
                <Form.Item label='' >
                    {getFieldDecorator('diagnose', { rules: [{ ...rules.required  }],initialValue:ok?data["diagnose"]:""
                    })(
                        <Radio.Group>
                            <Radio value={"0"}>是</Radio>
                            <Radio value={"1"}>否</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>3.确认为何种病症</span>
                <Row >
                    <Col md={16} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('oculopathy', { rules: [{ ...rules.required  }],initialValue:ok?data["oculopathy"]:''
                            })(
                                <Radio.Group onChange={this.oculopathyOtherIsCan}>
                                    <Radio value={"0"}>近视</Radio>
                                    <Radio value={"1"}>远视</Radio>
                                    <Radio value={"2"}>弱视</Radio>
                                    <Radio value={"3"}>斜视</Radio>
                                    <Radio value={"4"}>其他</Radio>
                                </Radio.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='其他' >
                            {getFieldDecorator('oculopathyOther', { rules: [{ required: oculopathyOtherIsCan }],initialValue:ok?data["oculopathyOther"]:''
                            })(
                                <Input  disabled={!oculopathyOtherIsCan} />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>4.发现症状后采用何种矫正方法</span>
                <Form.Item label='' >
                    {getFieldDecorator('correctionMethod', { rules: [{ ...rules.required  }],initialValue:ok?data["correctionMethod"]:''
                    })(
                        <Checkbox.Group >
                            <Checkbox value="0">训练</Checkbox>
                            <Checkbox value="1">配镜</Checkbox>
                            <Checkbox value="2">手术</Checkbox>
                        </Checkbox.Group>
                    )}
                </Form.Item>
                <span>5.效果如何</span>
                <Form.Item label='' >
                    {getFieldDecorator('effect', { rules: [{ ...rules.required  }],initialValue:ok?data["effect"]:''
                    })(
                        <Radio.Group>
                            <Radio value={"0"}>效果好</Radio>
                            <Radio value={"1"}>效果不佳</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>6.现视力情况</span>
                <Form.Item label='' >
                    {getFieldDecorator('visualAcuity', { rules: [{ ...rules.required  }],initialValue:ok?data["visualAcuity"]:''
                    })(
                        <Radio.Group>
                            <Radio value={"0"}>无变化</Radio>
                            <Radio value={"1"}>继续下降</Radio>
                            <Radio value={"2"}>有提升</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <div>遗传因素排查</div>
                <span>7.父亲</span>
                <Row >
                    <Col md={18} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('fVisionCondition', { rules: [{ ...rules.required  }],initialValue:ok?data["fVisionCondition"]:'0'
                            })(
                                <Radio.Group onChange={this.fVisionConditionIsCan}>
                                    &nbsp;&nbsp;近视(<Radio value={"0"}>轻</Radio>
                                    <Radio value={"1"}>中</Radio>
                                    <Radio value={"2"}>重)</Radio>
                                    远视(<Radio value={"3"}>轻</Radio>
                                    <Radio value={"4"}>中</Radio>
                                    <Radio value={"5"}>重)</Radio>
                                    &nbsp;&nbsp;&nbsp;散光( <Radio value={"6"}>轻</Radio>
                                    <Radio value={"7"}>中</Radio>
                                    <Radio value={"8"}>重)</Radio>
                                    <Radio value={"9"}>斜视</Radio>
                                    <Radio value={"10"}>弱视</Radio>
                                    <Radio value={"11"}>其他眼病</Radio>
                                    <Radio value={"12"}>无</Radio>
                                </Radio.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='其他眼病 ' >
                            {getFieldDecorator('fOther', { rules: [{ required:fVisionConditionIsCan }],initialValue:ok?data["fOther"]:''
                            })(
                                <Input  disabled={!fVisionConditionIsCan} />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>8.母亲</span>
                <Row >
                    <Col md={18} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('mVisionCondition', { rules: [{ ...rules.required  }],initialValue:ok?data["mVisionCondition"]:''
                            })(
                                <Radio.Group onChange={this.mVisionConditionIsCan}>
                                    &nbsp;&nbsp;近视(<Radio value={"0"}>轻</Radio>
                                    <Radio value={"1"}>中</Radio>
                                    <Radio value={"2"}>重)</Radio>
                                    远视(<Radio value={"3"}>轻</Radio>
                                    <Radio value={"4"}>中</Radio>
                                    <Radio value={"5"}>重)</Radio>
                                    &nbsp;&nbsp;&nbsp;散光( <Radio value={"6"}>轻</Radio>
                                    <Radio value={"7"}>中</Radio>
                                    <Radio value={"8"}>重)</Radio>
                                    <Radio value={"9"}>斜视</Radio>
                                    <Radio value={"10"}>弱视</Radio>
                                    <Radio value={"11"}>其他眼病</Radio>
                                    <Radio value={"12"}>无</Radio>
                                </Radio.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='其他眼病' >
                            {getFieldDecorator('mOther', { rules: [{ required:mVisionConditionIsCan  }],initialValue:ok?data["mOther"]:''
                            })(
                                <Input disabled={!mVisionConditionIsCan}  />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>睡眠状况</div>
                <br/><br/>
                <span>9.睡眠状况</span>
                <Form.Item label='' >
                    {getFieldDecorator('sleepingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["sleepingTime"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>6小时</Radio>
                            <Radio value={"1"}>7小时</Radio>
                            <Radio value={"2"}>8小时</Radio>
                            <Radio value={"3"}>9小时</Radio>
                            <Radio value={"4"}>10小时</Radio>
                            <Radio value={"5"}>11小时</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <hr/>
                <div className={styles.titleSeting}>用眼习惯采集</div>
                <br/><br/>
                <span>10.用眼项目</span>
                <Row >
                    <Col md={16} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('eyeProject', { rules: [{ ...rules.required  }],initialValue:ok?data["eyeProject"]:''
                            })(
                                <Checkbox.Group onChange={this.eyeProjectOtherIsCan} >
                                    <Checkbox value="0">看书</Checkbox>
                                    <Checkbox value="1">看电视</Checkbox>
                                    <Checkbox value="2">玩手机平板</Checkbox>
                                    <Checkbox value="3">其他</Checkbox>
                                </Checkbox.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={8} sm={24} >
                        <Form.Item label='用眼项目其他' >
                            {getFieldDecorator('eyeProjectOther', { rules: [{ required:eyeProjectOtherIsCan  }],initialValue:ok?data["eyeProjectOther"]:''
                            })(
                                <Input disabled={!eyeProjectOtherIsCan}  />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>11.每次看书、作业的时长</span>
                <Form.Item label='' >
                    {getFieldDecorator('readingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["readingTime"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>1小时</Radio>
                            <Radio value={"1"}>1-2小时</Radio>
                            <Radio value={"2"}>2-3小时</Radio>
                            <Radio value={"3"}>3-4小时</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>12.看书距离</span>
                <Form.Item label='' >
                    {getFieldDecorator('readingDistance', { rules: [{ ...rules.required  }],initialValue:ok?data["readingDistance"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>10厘米</Radio>
                            <Radio value={"1"}>10-15厘米</Radio>
                            <Radio value={"2"}>15-20厘米</Radio>
                            <Radio value={"3"}>20-30厘米</Radio>
                            <Radio value={"4"}>30厘米</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>13.单次看电视时长</span>
                <Form.Item label='' >
                    {getFieldDecorator('watchingTime', { rules: [{ ...rules.required  }],initialValue:ok?data["watchingTime"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>1小时</Radio>
                            <Radio value={"1"}>1-2小时</Radio>
                            <Radio value={"2"}>2-3小时</Radio>
                            <Radio value={"3"}>3-4小时</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>14.看电视距离</span>
                <Form.Item label='' >
                    {getFieldDecorator('watchingDistance', { rules: [{ ...rules.required  }],initialValue:ok?data["watchingDistance"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>2米</Radio>
                            <Radio value={"1"}>2-3米</Radio>
                            <Radio value={"2"}>3-4米</Radio>
                            <Radio value={"3"}>4米</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>15.用眼姿势（有无）</span>
                <Row labelCol={{ span: 5 }} wrapperCol={{ span: 18 }}>
                    <Col md={18} sm={24}>
                        <Form.Item label='' >
                            {getFieldDecorator('eyePosition', { rules: [{ ...rules.required  }],initialValue:ok?data["eyePosition"]:''
                            })(
                                <Checkbox.Group onChange={this.eyePositionOtherIsCan}>
                                    <Checkbox value="0">歪头</Checkbox>
                                    <Checkbox value="1">斜眼</Checkbox>
                                    <Checkbox value="2">躺着看</Checkbox>
                                    <Checkbox value="4">在车上看</Checkbox>
                                    <Checkbox value="5">走路看书</Checkbox>
                                    <Checkbox value="6">其他</Checkbox>
                                </Checkbox.Group>
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={6} sm={24} >
                        <Form.Item label='其他' >
                            {getFieldDecorator('eyePositionOther', { rules: [{ required:eyePositionOtherIsCan  }],initialValue:ok?data["eyePositionOther"]:''
                            })(
                                <Input disabled={!eyePositionOtherIsCan}  />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <hr/>
                <div className={styles.titleSeting}>用眼环境</div>
                <br/><br/>
                <span>16.家庭环境光线</span>
                <Form.Item label='' >
                    {getFieldDecorator('homeLightingEnvironment', { rules: [{ ...rules.required  }],initialValue:ok?data["homeLightingEnvironment"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>强光</Radio>
                            <Radio value={"1"}>适宜</Radio>
                            <Radio value={"2"}>偏暗</Radio>
                            <Radio value={"3"}>暗</Radio>
                            <Radio value={"4"}>光线闪烁</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <span>17.教室环境光线</span>
                <Form.Item label='' >
                    {getFieldDecorator('classroomLightingEnvironment', { rules: [{ ...rules.required  }],initialValue:ok?data["classroomLightingEnvironment"]:''
                    })(
                        <Radio.Group >
                            <Radio value={"0"}>强光</Radio>
                            <Radio value={"1"}>适宜</Radio>
                            <Radio value={"2"}>偏暗</Radio>
                            <Radio value={"3"}>暗</Radio>
                            <Radio value={"4"}>光线闪烁</Radio>
                        </Radio.Group>
                    )}
                </Form.Item>
                <hr/>
                <div className={styles.titleSeting}>视力检查</div>
                <br/><br/>
                <span>18.裸眼远视力（5米）</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='右眼' >
                            {getFieldDecorator('rD', { rules: [{ ...rules.required  }],initialValue:ok?data["rD"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='左眼' >
                            {getFieldDecorator('lD', { rules: [{ ...rules.required  }],initialValue:ok?data["lD"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <span>19.矫正远视力（5米）</span>
                <Row >
                    <Col md={12} sm={24}>
                        <Form.Item label='右眼' >
                            {getFieldDecorator('rCva', { rules: [{ ...rules.required  }],initialValue:ok?data["rCva"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='左眼' >
                            {getFieldDecorator('lCva', { rules: [{ ...rules.required  }],initialValue:ok?data["lCva"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col md={12} sm={24}>
                        <Form.Item label='咨询导师' >
                            {getFieldDecorator('tutor', { rules: [{ ...rules.required  }],initialValue:ok?data["tutor"]:''
                            })(
                                <Input   />
                            )}
                        </Form.Item>
                    </Col>
                    <Col md={12} sm={24} >
                        <Form.Item label='时间' >
                            {getFieldDecorator('gmtCreate', { initialValue:moment(moment().format(dateFormat.day_hour), dateFormat.day_hour)
                            })(
                                <DatePicker disabled format="YYYY-MM-DD HH:mm:ss" />
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

export default ConsultationDrawer;