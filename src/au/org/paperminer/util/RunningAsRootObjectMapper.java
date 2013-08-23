package au.org.paperminer.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate.HibernateModule;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RunningAsRootObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812395611309190649L;
	@Autowired
	ApplicationContext applicationContext;

	public RunningAsRootObjectMapper() {
		// Problems serializing Hibernate lazily initialized collections? Fix
		// here.
		HibernateModule hm = new HibernateModule();
		hm.configure(HibernateModule.Feature.FORCE_LAZY_LOADING, true);
		this.registerModule(hm);

		// Jackson confused by what to set or by extra properties? Fix it.
		//this.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        //this.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //this.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		this.setSerializationInclusion(Include.NON_NULL);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	@Override
	@Autowired
	public Object setHandlerInstantiator(HandlerInstantiator hi) {
		return super.setHandlerInstantiator(hi);
	}
}