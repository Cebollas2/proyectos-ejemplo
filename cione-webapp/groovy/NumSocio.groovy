import info.magnolia.cms.security.*;
import info.magnolia.cms.beans.config.ContentRepository;
import info.magnolia.cms.security.auth.ACL;
import org.apache.commons.lang.StringUtils;
import info.magnolia.cms.security.PermissionImpl;
import java.lang.String;

allUsersCione = SecuritySupport.Factory.getInstance().getUserManager().getUsersWithRole("cione");
allUsers = SecuritySupport.Factory.getInstance().getUserManager().getAllUsers();
println('*************************************');
println('numero de usuarios ' + allUsersCione.size());
println('*************************************');

allUsersCione.each(){name ->
    println(name)
}