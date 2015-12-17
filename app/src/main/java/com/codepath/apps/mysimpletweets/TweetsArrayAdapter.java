package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by tammy.ngo on 12/9/2015.
 */
// Taking Tweets object and turning them inot views display
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // view holder pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Get the tweet
        Tweet tweet = getItem(position);
        TweetsViewHolder viewHolder = null;
        // 2. Find or inflate template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new TweetsViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TweetsViewHolder) convertView.getTag();
        }

        //3. Populate data into subviews
        viewHolder.tvUserName.setText("@" + tweet.getUser().getScreenName());
        viewHolder.tvFullName.setText(tweet.getUser().getName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent); // clear old image
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);

        String timeCreated = tweet.getCreatedAt();
        String Format = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(Format, Locale.ENGLISH);
        sf.setLenient(true);
        String formatDate = "";

        try {
            long dateMillis = sf.parse(timeCreated).getTime();
            formatDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            e.toString();
        }
        if (!formatDate.isEmpty()) {
            String[] timespanParts = formatDate.split(" ");
            String formatTime = timespanParts[0] + timespanParts[1].charAt(0);
            if (formatTime.contains("In")) {
                formatTime = timespanParts[1] + timespanParts[2].charAt(0);
            }

            viewHolder.tvCreateAt.setText(formatTime);
        }
        return convertView;
    }
}
