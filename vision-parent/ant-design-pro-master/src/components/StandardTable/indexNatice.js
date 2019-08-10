import React, { PureComponent, Fragment } from 'react';
import { Table, Alert } from 'antd';
import styles from './index2.less';
import cookie from 'react-cookies';
import {FormdateFormat} from '@/utils/dataUtils';
import { dateFormat } from '../../../config/utilsConfig';


function initTotalList(columns) {
  const totalList = [];
  columns.forEach(column => {
    if (column.needTotal) {
      totalList.push({ ...column, total: 0 });
    }
  });
  return totalList;
}

class StandardTable extends PureComponent {
  constructor(props) {
    super(props);
    const { columns } = props;
    const needTotalList = initTotalList(columns);

    this.state = {
      selectedRowKeys: [],
      needTotalList,
      initHeight: 377,
    };
  }

  //自动配置页面高度
  componentWillReceiveProps(nextProps) {  }

  componentDidUpdate(){
      //自动配置页面高度
      const {expandForm,expandId} = this.props
      var cH = document.body.clientHeight;
      var h = 0;
      if(document.getElementById(expandId)){
        var o = document.getElementById(expandId);
        h = o.offsetHeight;  //高度
      }
      // if (expandForm) {
      //   this.setState({ initHeight: cH - 214 - h});
      // }else{
        this.setState({ initHeight: cH - 214 - h });
      // }
      if(expandId=="advancedRoleOfUsers"){
        this.setState({ initHeight: cH - 147 - h });
      }

      

  }

  //选择框使用方法
  handleRowSelectChange = (selectedRowKeys, selectedRows) => {
    
    let needTotalList = [...this.state.needTotalList];
    needTotalList = needTotalList.map(item => {
      return {
        ...item,
        total: selectedRows.reduce((sum, val) => {
          return sum + parseFloat(val[item.dataIndex], 10);
        }, 0),
      };
    });
    const { type , dispatch } = this.props;
    //改变删除按钮状态和选中id
    dispatch({
      type: type+'/selectRows',
      payload: {
        selectedRowKeys:selectedRowKeys,
        selectedRows:selectedRows,
        deleteDisabled:selectedRowKeys.length!=0?false:true,
      },
    });
    this.setState({ selectedRowKeys, needTotalList });
  };

  //分页条件改变时方法（翻页、分页条件查询等)
  handleTableChange = (pagination, filters,sorter) => {
    const { type , dispatch , queryFormData ,parentId} = this.props;
    //查询条件中时间格式化
    let newQueryFormData = FormdateFormat(queryFormData,dateFormat.day_hour);
    //从cookie取出分页查询页面大小与分页对象中传来的值比较
    if(cookie.load('limit')!=pagination.pageSize){
      cookie.save('limit',pagination.pageSize);
    }

    //组装分页查询参数
    const params = {
            pageCurrent: pagination.current,
            start: ((pagination.current-1)*pagination.pageSize),
            orgId: 1,//根据tree节点Id查询，没有就为''
            ...newQueryFormData,//分页条件
    };
    //发起分页查询
    let dispatchType = type+'/fetch';
    const strArray =type.split('');
    for(let k of strArray){
      if(k=='/') dispatchType=type;
    }
    dispatch({
            type: dispatchType,
            payload: params,
        });
    
  };

  //设置左侧页码显示
  showTotal=(total,range)=>{
    // 第{range[0]}到{range[1]}条
    //已选中条&nbsp;&nbsp;&nbsp;&nbsp;
    return(
      <div>总共{total}条数据</div>
    );
  }

  render() {
    const {  needTotalList } = this.state;//selectedRowKeys,
    const { data, loading, columns, rowKey, selectedRows, scroll, hidpagination, onRow, deleteDisabled, selectedRowKeys  } = this.props;
    const paginationProps = {
      showSizeChanger: true,
      showQuickJumper: true,
      pageSizeOptions:['1','5','10','20','50','100','200'],
      showTotal: (total,range) => this.showTotal(total,range),
      ...data.pagination
    };
    const rowSelection = {
      selectedRowKeys,
      onChange: this.handleRowSelectChange,
      getCheckboxProps: record => ({
        disabled: record.disabled,
      }),
    };
    return (
      
      <div className={styles.standardTable}>
     
        <Table
          loading={loading}
          rowKey={rowKey || 'id'}
          rowSelection={selectedRows ? rowSelection : null}
          dataSource={data.list}
          columns={columns}
          bordered
          size="small"
          scroll={{y: this.state.initHeight,...scroll }}
          pagination={hidpagination?false:paginationProps}
          onChange={this.handleTableChange} 
          onRow={onRow} 
        />
      </div>
    );
  }
}

export default StandardTable;
