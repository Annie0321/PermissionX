package com.permissionx.anniedev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

/**
 * 并没有重写onCreateView()方法来加载某个布局，因此它自然就是一个不可见的Fragment，要想使用它只需要将它添加到Activity中即可
 */

typealias PermissionCallback = (Boolean, List<String>) -> Unit

class InvisibleFragment: Fragment() {
    // callback变量作为运行时权限申请结果的回调通知方式
    private var callback: PermissionCallback? = null

    fun requestNow(cb: PermissionCallback, vararg permissions: String){
        callback = cb
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            val deniedList = ArrayList<String>()
            for((index, result) in grantResults.withIndex()){
                if(result != PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            val allGranted = deniedList.isEmpty()
            callback?.let {
                it(allGranted, deniedList)
            }
        }
    }
}