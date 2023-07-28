# Change Log

## 2023-07-28

* `bool` values in azuredeploy.json should not be quoted (eg `true` rather than `"true"`) otherwise az validation fails.
* cp in `deploy.sh` includes deploy-* dirs which result in cp to self cannot be done errors.
* `kubernetesVersion` in azuredeploy.json needs a later version (1.22.4 changed to 1.27.3).
* Instructions were incorrect and needed updating.