import React, { Fragment, Component } from 'react';
import { connect } from 'dva';
import { Input, Button, Drawer, Form, InputNumber, Select, Radio, Row, Col, Table, Tooltip, message,Menu, Icon } from 'antd';
import StandardTable from '@/components/StandardTable/indexNatice';
import configStyles from '@/less/config.less';
import {deleteData} from '@/utils/dataUtils';
import moment from 'moment';
import BaseDicTree from '@/components/BaseTree/BaseDicTree';


const FormItem = Form.Item;
const Option = Select.Option;
const { SubMenu } = Menu;
//创建表单
//创建表单
@Form.create()

@connect(({preSymptomType, loading }) => ({
    preSymptomType,
    loading: loading.models.preSymptomType,
}))
class PreSymptomType extends Component {
    constructor(props) {
        super(props);
        this.state = {  };
    }

    componentDidMount=()=>{
        this.getPreSymptomTypeAll();
    }

    getPreSymptomTypeAll=(value)=>{
        const { dispatch } = this.props;
        dispatch({
            type:"preSymptomType/getPreSymptomTypeAll",
            payload:{
              
                ...value,
            }
        });
    }
    onSelect=(e)=>{
      debugger;
    }

    render() {


        const { preSymptomType: { data, selectedRows,preRow, deleteDisabled, msg, selectedRowKeys, treeList }, 
        form: { getFieldDecorator,getFieldsValue }, loading, dispatch} = this.props;
        const treeNodeLists = [treeList]
        return(
            <div>
              <BaseDicTree
                            onSelect={this.onSelect}
                            treeList={treeNodeLists}
                            defaultExpandedKeys={[treeNodeLists[0].id]}
                        />
            </div>
        );
    }
}

export default PreSymptomType;

