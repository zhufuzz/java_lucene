 /**  
 *@Description:     
 */ 
package com.jikexueyuan.test;  

import java.util.HashSet;

import com.jikexueyuan.lucene.index.manager.IndexManager;
import com.jikexueyuan.lucene.index.model.ConfigBean;
import com.jikexueyuan.lucene.index.model.IndexConfig;
 @SuppressWarnings("all")
public class IndexManagerTest {

	public static void main(String[] args) {
		HashSet<ConfigBean> set = new HashSet<ConfigBean>();
		for (int i = 0; i < 4; i++) {
			ConfigBean bean = new ConfigBean();
			bean.setIndexPath("d:/jkxy");
			bean.setIndexName("test" + i);
			set.add(bean);
		}
		IndexConfig.setConfig(set);
		IndexManager indexManager1 = IndexManager.getIndexManager("test0");
	}

}
