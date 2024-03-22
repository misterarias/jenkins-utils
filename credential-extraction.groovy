/*
* Groovy Console script
* Used to get ALL credentials in plain text.
* Admins love this
*/
import com.cloudbees.plugins.credentials.Credentials

Set<Credentials> allCredentials = new HashSet<Credentials>();
allCredentials.addAll com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
      com.cloudbees.plugins.credentials.Credentials.class
)

allCredentials.addAll Jenkins.instance.getAllItems(com.cloudbees.hudson.plugins.folder.Folder.class).collect{ folder ->
        folder.items.collect{ item ->
                com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
                com.cloudbees.plugins.credentials.Credentials.class, item as hudson.model.Item
        )
    }
}.flatten()

allCredentials.sort{ it -> it.id }.each{ cred ->
  println("id: ${cred.id}")
  for (prop in cred.properties) {
    println(prop)
  }
  println("")
} 
