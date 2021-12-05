package pl.gg.weather.functional

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View

class SavedState : View.BaseSavedState {

    var bundle: Bundle? = null

    constructor(superState: Parcelable?) : super(superState)

    constructor(source: Parcel) : super(source) {
        bundle = source.readBundle(javaClass.classLoader)
    }

    override fun writeToParcel(out: Parcel?, flags: Int) {
        super.writeToParcel(out, flags)
        out?.writeBundle(bundle)
    }

    companion object{
        @JvmField
        val CREATOR: Parcelable.Creator<SavedState?> = object : Parcelable.Creator<SavedState?> {
            override fun createFromParcel(`in`: Parcel): SavedState? {
                return SavedState(`in`)
            }

            override fun newArray(size: Int): Array<SavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}