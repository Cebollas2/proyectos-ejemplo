import info.magnolia.cms.security.SecuritySupport;
/*
* Este groovy contabiliza el numero de socios del sistema
*/
public class NumSociosCommand {
    
    public static void main(String[] args){
        Collection<String> allUsersCione = SecuritySupport.Factory.getInstance().getUserManager().getUsersWithRole("cione");
        println "hola"
        println('*************************************');
        println('numero de usuarios ' + allUsersCione.size());
        println('*************************************');

        allUsersCione.each(){name ->
            println(name)
        }
    }
}