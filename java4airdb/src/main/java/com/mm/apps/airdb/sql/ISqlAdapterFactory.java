package com.mm.apps.airdb.sql;

import java.sql.Connection;

public interface ISqlAdapterFactory {
	
	ISqlAdapter getSqlAdapter(IQuery query,Connection conn);

}
