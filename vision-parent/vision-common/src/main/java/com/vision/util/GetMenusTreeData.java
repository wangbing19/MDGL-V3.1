package com.vision.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vision.pojo.exp.ExpRemoteDiagnose;
import com.vision.vo.TreeDiaMenus;
import com.vision.vo.TreeMenus;
import com.vision.vo.TreeStructure;

public class GetMenusTreeData<T> {


	/***
	 * 
	 * @param treeData 封装的数据
	 * @return 返回树结构的数据
	 */
	public TreeMenus<T> getTree(List<TreeMenus<T>> list){
		//获取所有父级id
		Long[] pIds = new Long[list.size()];
		Long[] pIds2 = new Long[list.size()];
		int y,l;
		int k=0;
		for(int i=0;i<list.size();i++) {
			Long pId = list.get(i).getpId();
			if(pId!=null&&pId!=0L) {
				if(k>0) {
					for(y=0;y<k;y++) {
						if(pIds[y]==pId)
							break;
					}				
					if(y==k) { pIds[k] = pId; }
					 
					k++;
				}else if(k==0){
					pIds[k] = pId;
					k++;
				}
			}else {
				pIds[0] = 0L;
			}
		}
		int p=0;
		for(l=0;l<list.size();l++) {
			if(pIds[l]!=null) {
				pIds2[p] = pIds[l];
				p++;
			}
		}
		
		List<TreeMenus<T>> list1 = new ArrayList<>();
		
		for(int ss=0;pIds2[ss]!=null;ss++) {
			for(int s=0;s<list.size();s++) {
				if(list.get(s).getpId()==null||list.get(s).getpId()==0L) {
					list1.add(list.get(s));
				}
				if(list.get(s).getpId()==pIds2[ss]) {
					list1.add(list.get(s));
				}
			}
		}		
		Map<Long,List<TreeMenus<T>>> map = new HashMap<>();
		int pIdCon=0;
		for(int j=0;j<list1.size();) {
			List<TreeMenus<T>> treeStructure = new ArrayList<>();
			Long pIdLong = list1.get(j).getpId();
			if(pIdLong==null||pIdLong==0L) {
				treeStructure.add(list1.get(j));
				map.put(0L,treeStructure);
				j++;
			}else {
				while(pIds2[pIdCon]!=null&&list1.get(j).getpId()==pIds2[pIdCon]) {
					treeStructure.add(list1.get(j));
					j++;
					if(j==list1.size())  break;
				}
				pIdCon++;
				map.put(pIdLong, treeStructure);
			}
			if(pIds2[pIdCon]==null) break;		
		}
		
		for(int m=0;m<list1.size();m++) {
			Long id = list1.get(m).getKey();
			for(int n=0;pIds2[n]!=null;n++) {
				if(id==pIds2[n]) {
					List<TreeMenus<T>> mapList = map.get(id);
					list1.get(m).setChildren(mapList);
					break;
				}
			}
		}
		return list1.get(0);
	}
	
	/***
	 * 
	 * @param treeData 封装的数据
	 * @return 返回树结构的数据
	 */								 		
	public TreeDiaMenus<T> getTreeDia(List<TreeDiaMenus<T>> list){
		//获取所有父级id
		Long[] pIds = new Long[list.size()];
		Long[] pIds2 = new Long[list.size()];
		int y,l;
		int k=0;
		for(int i=0;i<list.size();i++) {
			Long pId = list.get(i).getpId();
			if(pId!=null&&pId!=0L) {
				if(k>0) {
					for(y=0;y<k;y++) {
						if(pIds[y]==pId)
							break;
					}				
					if(y==k) { pIds[k] = pId; }
					 
					k++;
				}else if(k==0){
					pIds[k] = pId;
					k++;
				}
			}else {
				pIds[0] = 0L;
			}
		}
		int p=0;
		for(l=0;l<list.size();l++) {
			if(pIds[l]!=null) {
				pIds2[p] = pIds[l];
				p++;
			}
		}
		
		List<TreeDiaMenus<T>> list1 = new ArrayList<>();
		
		for(int ss=0;pIds2[ss]!=null;ss++) {
			for(int s=0;s<list.size();s++) {
				if(list.get(s).getpId()==null||list.get(s).getpId()==0L) {
					list1.add(list.get(s));
				}
				if(list.get(s).getpId()==pIds2[ss]) {
					list1.add(list.get(s));
				}
			}
		}		
		Map<Long,List<TreeDiaMenus<T>>> map = new HashMap<>();
		int pIdCon=0;
		for(int j=0;j<list1.size();) {
			List<TreeDiaMenus<T>> treeStructure = new ArrayList<>();
			Long pIdLong = list1.get(j).getpId();
			if(pIdLong==null||pIdLong==0L) {
				treeStructure.add(list1.get(j));
				map.put(0L,treeStructure);
				j++;
			}else {
				while(pIds2[pIdCon]!=null&&list1.get(j).getpId()==pIds2[pIdCon]) {
					treeStructure.add(list1.get(j));
					j++;
					if(j==list1.size())  break;
				}
				pIdCon++;
				map.put(pIdLong, treeStructure);
			}
			if(pIds2[pIdCon]==null) break;		
		}
		
		for(int m=0;m<list1.size();m++) {
			Long id = list1.get(m).getKey();
			for(int n=0;pIds2[n]!=null;n++) {
				if(id==pIds2[n]) {
					List<TreeDiaMenus<T>> mapList = map.get(id);
					list1.get(m).setChildren(mapList);
					break;
				}
			}
		}
		return list1.get(0);
	}

}
