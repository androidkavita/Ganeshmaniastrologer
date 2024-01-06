package com.callastro.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.callastro.R
import com.callastro.adapters.ReviewRatingSelectAdapter
import com.callastro.model.GetAstroRatingReviewReviewRatings
import com.callastro.viewModels.RatingReviewViewModel
import com.maxtra.callastro.baseClass.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class RatingReviewsActivity : BaseActivity(), ReviewRatingSelectAdapter.OnClick {

    lateinit var binding: com.callastro.databinding.ActivityRatingReviewsBinding
    private val ratingReviewViewModel: RatingReviewViewModel by viewModels()

    lateinit var reviewRatingAllDataId: ArrayList<GetAstroRatingReviewReviewRatings>
    lateinit var getSelectRatingAdapter: ReviewRatingSelectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rating_reviews)

        binding.backArrow.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.tvHeadName.text = "Select Rating & Reviews"

        reviewRatingAllDataId = ArrayList()

        ratingReviewViewModel.ratingReviewAllResponse.observe(this) {
            if (it.status == 1) {



                reviewRatingAllDataId.clear()
                reviewRatingAllDataId.addAll(it.data!!.reviewRatings)
                getSelectRatingAdapter =
                    ReviewRatingSelectAdapter(this, reviewRatingAllDataId, this)
                binding.rvSelectRatingReview.adapter = getSelectRatingAdapter
                binding.rvSelectRatingReview.visibility = View.VISIBLE
                binding.tvCount.setText(it.data!!.counts!!.totalPin.toString() + "/" + it.data!!.counts!!.totalReview.toString())
                binding.tvRating.setText("Total Rating: "+it.data?.astroRating?.avgRating.toString())

                binding.llTopDetails.visibility = View.VISIBLE
                binding.idNouser.visibility = View.GONE




            } else {
                toast(this, it.message.toString())
                binding.rvSelectRatingReview.visibility = View.GONE
                binding.llTopDetails.visibility = View.GONE
                binding.idNouser.visibility = View.VISIBLE


            }
        }

        ratingReviewViewModel.astro_rating_review_listApi("Bearer " + userPref.getToken())

        ratingReviewViewModel.ratingReviewUpdateResponse.observe(this) {
            if (it.status == 1) {
                ratingReviewViewModel.astro_rating_review_listApi("Bearer " + userPref.getToken())
            } else {
                toast(this, it.message!!)
            }
        }

        binding.btnDone.setOnClickListener(View.OnClickListener {
            finish()
        })

    }



    override fun onRatingItemClicked(getAstroRatingReviewReviewRatings: GetAstroRatingReviewReviewRatings, checkAdd :String) {

        if(checkAdd.equals("0")){
            ratingReviewViewModel.review_pin_updateApi("Bearer " + userPref.getToken(), getAstroRatingReviewReviewRatings.id.toString(),"0")
        }
        else if(checkAdd.equals("1")){
            ratingReviewViewModel.review_pin_updateApi("Bearer " + userPref.getToken(), getAstroRatingReviewReviewRatings.id.toString(),"1")
        }

    }

}