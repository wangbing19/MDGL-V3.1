package com.vision.util;
/**获取树的数据*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vision.vo.TreeStructure;

public class GetTreeData<T> {

	/***
	 * 
	 * @param treeData 封装的数据
	 * @return 返回树结构的数据
	 */
	public List<TreeStructure<T>>  getTree(List<TreeStructure<T>> list){
		//获取所有父级id
		Long[] pIds = new Long[list.size()];
		int k=0;
		for(int i=0;i<list.size();i++) {
			Long pId = list.get(i).getParentId();
			if(pId!=null&&pId!=0L) {
				if(k>0&&pIds[k-1]!=pId) {
					pIds[k] = pId;
					k++;
				}else if(k==0){
					pIds[k] = pId;
					k++;
				}
			}else {
				pIds[0] = 0L;
			}
		}
		
		Map<Long,List<TreeStructure<T>>> map = new HashMap<>();
		int pIdCon=0;
		for(int j=0;j<list.size();) {
			List<TreeStructure<T>> treeStructure = new ArrayList<>();
			Long pIdLong = list.get(j).getParentId();
			if(pIdLong==null||pIdLong==0L) {
				treeStructure.add(list.get(j));
				map.put(0L,treeStructure);
				j++;
			}else {
				while(pIds[pIdCon]!=null&&list.get(j).getParentId()==pIds[pIdCon]) {
					treeStructure.add(list.get(j));
					j++;
					if(j==list.size())  break;
				}
				pIdCon++;
				map.put(pIdLong, treeStructure);
			}
			if(pIds[pIdCon]==null) break;		
		}
		
		for(int m=0;m<list.size();m++) {
			Long id = list.get(m).getId();
			for(int n=0;pIds[n]!=null;n++) {
				if(id==pIds[n]) {
					List<TreeStructure<T>> mapList = map.get(id);
					list.get(m).setList(mapList);
					break;
				}
			}
		}
		return list;
	}
}























