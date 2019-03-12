@GenericGenerator(name="default", strategy = "org.hibernate.id.enhanced.TableGenerator"
		 ,   parameters = {
	        @Parameter(name="increment_size", value = "15")
	       ,@Parameter(name="prefer_entity_table_as_segment_value", value = "true")
	   }
	)
package cn.edu.llxy.dw.dss.po.brick;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
