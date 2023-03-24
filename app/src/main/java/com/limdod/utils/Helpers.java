package com.limdod.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.NonNull;

public class Helpers
{

    // public static double round(double value, int places) {
    //     if (places < 0) throw new IllegalArgumentException();
    //
    //     long factor = (long) Math.pow(10, places);
    //     value = value * factor;
    //     long tmp = Math.round(value);
    //     return (double) tmp / factor;
    // }
    //
    // public static void addServiceUpdate()
    // {
    //     FirebaseDatabase.getInstance().getReference()
    //             .child("saloon")
    //             .child(Session.USER.getId().toString())
    //             .child("service_count")
    //             .addListenerForSingleValueEvent(new ValueEventListener()
    //             {
    //                 @Override
    //                 public void onDataChange(@NonNull DataSnapshot snapshot)
    //                 {
    //                     if(snapshot.exists())
    //                     {
    //                         int number = Integer
    //                                 .parseInt(Objects.requireNonNull(snapshot.getValue()).toString());
    //
    //                         number++;
    //
    //                         FirebaseDatabase.getInstance().getReference()
    //                                 .child("saloon")
    //                                 .child(Session.USER.getId().toString())
    //                                 .child("service_count")
    //                                 .setValue(String.valueOf(number));
    //                     }
    //                     else
    //                     {
    //                         FirebaseDatabase.getInstance().getReference()
    //                                 .child("saloon")
    //                                 .child(Session.USER.getId().toString())
    //                                 .child("service_count")
    //                                 .setValue("1");
    //                     }
    //                 }
    //
    //                 @Override
    //                 public void onCancelled(@NonNull DatabaseError error)
    //                 {
    //
    //                 }
    //             });
    // }
    //
    // public static void addReviewUpdate(Review review, String saloonId)
    // {
    //     FirebaseDatabase.getInstance().getReference()
    //             .child("saloon")
    //             .child(saloonId)
    //             .child("user_feedback")
    //             .addListenerForSingleValueEvent(new ValueEventListener()
    //             {
    //                 @Override
    //                 public void onDataChange(@NonNull DataSnapshot snapshot)
    //                 {
    //                     if(snapshot.exists())
    //                     {
    //                         int numberOfReviews = Integer
    //                                 .parseInt(Objects.requireNonNull(snapshot.child("numOfReviews").getValue()).toString());
    //
    //                         double rating = Double
    //                                 .parseDouble(Objects.requireNonNull(snapshot.child("rating").getValue()).toString());
    //
    //                         numberOfReviews++;
    //                         rating = rating + review.getRating();
    //
    //                         double avgRating = rating/((double) numberOfReviews);
    //
    //                         UserFeedback feedback = new UserFeedback();
    //                         feedback.setRating(avgRating);
    //                         feedback.setNumOfReviews(numberOfReviews);
    //
    //                         FirebaseDatabase.getInstance().getReference()
    //                                 .child("saloon")
    //                                 .child(saloonId)
    //                                 .child("user_feedback")
    //                                 .setValue(feedback);
    //                     }
    //                     else
    //                     {
    //                         UserFeedback feedback = new UserFeedback(1, review.getRating());
    //                         FirebaseDatabase.getInstance().getReference()
    //                                 .child("saloon")
    //                                 .child(saloonId)
    //                                 .child("user_feedback")
    //                                 .setValue(feedback);
    //                     }
    //                 }
    //
    //                 @Override
    //                 public void onCancelled(@NonNull DatabaseError error)
    //                 {
    //
    //                 }
    //             });
    // }
    //
    // public static void removeServiceUpdate()
    // {
    //     FirebaseDatabase.getInstance().getReference()
    //             .child("saloon")
    //             .child(Session.USER.getId().toString())
    //             .child("service_count")
    //             .addListenerForSingleValueEvent(new ValueEventListener()
    //             {
    //                 @Override
    //                 public void onDataChange(@NonNull DataSnapshot snapshot)
    //                 {
    //                     if(snapshot.exists())
    //                     {
    //                         int number = Integer
    //                                 .parseInt(Objects.requireNonNull(snapshot.getValue()).toString());
    //
    //                         number--;
    //
    //                         FirebaseDatabase.getInstance().getReference()
    //                                 .child("saloon")
    //                                 .child(Session.USER.getId().toString())
    //                                 .child("service_count")
    //                                 .setValue(String.valueOf(number));
    //                     }
    //
    //                 }
    //
    //                 @Override
    //                 public void onCancelled(@NonNull DatabaseError error)
    //                 {
    //
    //                 }
    //             });
    // }
    //
    // public static void showDeletionDialog(Context context, String title, String message, ServiceCallback callback)
    // {
    //     AlertDialog.Builder builder = new AlertDialog.Builder(context);
    //     builder.setTitle(title);
    //     builder.setMessage(message);
    //     builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener()
    //     {
    //         @Override
    //         public void onClick(DialogInterface dialogInterface, int i)
    //         {
    //             callback.onServiceSelected(new SaloonService());
    //             dialogInterface.dismiss();
    //         }
    //     });
    //
    //     builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    //     {
    //         @Override
    //         public void onClick(DialogInterface dialogInterface, int i)
    //         {
    //             dialogInterface.dismiss();
    //         }
    //     });
    //
    //     builder.show();
    // }
    //
    // public static void showRatingDialog(Context context, User saloon)
    // {
    //     AlertDialog.Builder builder = new AlertDialog.Builder(context);
    //     DialogRatingBarBinding binding = DialogRatingBarBinding.inflate(LayoutInflater.from(context));
    //     builder.setView(binding.getRoot());
    //     AlertDialog dialog = builder.create();
    //
    //     binding.tvReview.setText("Rate " + saloon.getName());
    //
    //     binding.btnSubmit.setOnClickListener(new View.OnClickListener()
    //     {
    //         @Override
    //         public void onClick(View view)
    //         {
    //             float rating = binding.ratingBar.getRating();
    //             String review = binding.edtReview.getText().toString();
    //
    //             if(rating == 0f)
    //             {
    //                 Toast.makeText(context, "Please select rating", Toast.LENGTH_SHORT).show();
    //                 return;
    //             }
    //
    //             Review objReview = new Review(review, rating);
    //
    //             FirebaseDatabase.getInstance().getReference()
    //                     .child("saloon")
    //                     .child(saloon.getId().toString())
    //                     .child("reviews")
    //                     .push()
    //                     .setValue(objReview);
    //
    //             addReviewUpdate(objReview, saloon.getId().toString());
    //
    //             Toast.makeText(context, "Review Added!", Toast.LENGTH_SHORT).show();
    //
    //             dialog.dismiss();
    //
    //         }
    //     });
    //
    //
    //     dialog.show();
    //
    //
    //
    // }
    //
    // public static ArrayList<Booking> createSlots(String openTime, String closeTime, boolean isSameDay) throws ParseException
    // {
    //     SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    //     ArrayList<Booking> bookings = new ArrayList<>();
    //
    //     Calendar openTCal = Calendar.getInstance();
    //     Calendar closeTCal = Calendar.getInstance();
    //     Calendar currentTime = Calendar.getInstance();
    //
    //     openTCal.setTime(Objects.requireNonNull(timeFormat.parse(openTime)));
    //     closeTCal.setTime(Objects.requireNonNull(timeFormat.parse(closeTime)));
    //
    //
    //     while(openTCal.before(closeTCal))
    //     {
    //         if(isSameDay)
    //         {
    //             if(openTCal.get(Calendar.HOUR_OF_DAY) <= currentTime.get(Calendar.HOUR_OF_DAY))
    //             {
    //                 openTCal.add(Calendar.HOUR_OF_DAY, 1);
    //                 continue;
    //             }
    //         }
    //
    //         String slotTitle = timeFormat.format(openTCal.getTime());
    //         openTCal.add(Calendar.HOUR_OF_DAY, 1);
    //         String slotDuration = slotTitle + " - " + timeFormat.format(openTCal.getTime());
    //
    //         bookings.add(new Booking(
    //                 slotTitle,
    //                 slotDuration,
    //                 false
    //         ));
    //
    //     }
    //
    //
    //     return bookings;
    //
    // }
    //
    // public static int getHourOfDay(String time)
    // {
    //     int hour = Integer.parseInt(time.split(":")[0]);
    //     String amPm = time.split(" ")[1];
    //
    //     if(amPm.equals("PM") && hour != 12)
    //     {
    //         hour = hour + 12;
    //     }
    //
    //     if(amPm.equals("AM") && hour == 12)
    //     {
    //         hour = 0;
    //     }
    //
    //     return hour;
    // }

}
