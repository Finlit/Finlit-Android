package com.app.finlit.ui.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.finlit.R;
import com.app.finlit.ui.base.BaseActivity;

import butterknife.BindView;

public class PrivacyPolicyActivity extends BaseActivity {

    @BindView(R.id.tv_privacy)
    public TextView textView;

    @BindView(R.id.iv_left)
    public ImageView iv_back;

    @BindView(R.id.tv_heading)
    public TextView heading;


    @Override
    protected int getContentId() {
        return R.layout.activity_privacy_policy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        textView.setText("Privacy\n" +
                "Last updated January 24, 2019\n" +
                "\n" +
                "Thank you for choosing to be part of our community at Finlit Dating (“Company”, “we”, “us”, or “our”). We are committed to protecting your personal information and your right to privacy. If you have any questions or concerns about our policy, or our practices with regards to your personal information, please contact us at letsmeet@finlitdating.com.\n" +
                "\n" +
                "When you visit our website www.finlitdating.com, mobile application, and use our services, you trust us with your personal information. We take your privacy very seriously. In this privacy notice, we describe our privacy policy. We seek to explain to you in the clearest way possible what information we collect, how we use it and what rights you have in relation to it. We hope you take some time to read through it carefully, as it is important. If there are any terms in this privacy policy that you do not agree with, please discontinue use of our Sites or Apps and our services.\n" +
                "\n" +
                "This privacy policy applies to all information collected through our website (such as www.finlitdating.com), mobile application, (\"Apps\"), and/or any related services, sales, marketing or events (we refer to them collectively in this privacy policy as the \"Sites\").\n" +
                "\n" +
                "Please read this privacy policy carefully as it will help you make informed decisions about sharing your personal information with us.\n" +
                "\n" +
                " \n" +
                "\n" +
                "TABLE OF CONTENTS\n" +
                "\n" +
                "1.       WHAT INFORMATION DO WE COLLECT?\n" +
                "\n" +
                "2.       HOW DO WE USE YOUR INFORMATION?\n" +
                "\n" +
                "3.       WILL YOUR INFORMATION BE SHARED WITH ANYONE?\n" +
                "\n" +
                "4.       WHO WILL YOUR INFORMATION BE SHARED WITH?\n" +
                "\n" +
                "5.       HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                "\n" +
                "6.       HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                "\n" +
                "7.       HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                "\n" +
                "8.       DO WE COLLECT INFORMATION FROM MINORS?\n" +
                "\n" +
                "9.       WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                "\n" +
                "10.   CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                "\n" +
                "11.   DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                "\n" +
                "12.   DO WE MAKE UPDATES TO THIS POLICY?\n" +
                "\n" +
                "13.   HOW CAN YOU CONTACT US ABOUT THIS POLICY?\n" +
                "\n" +
                " \n" +
                "\n" +
                "1. WHAT INFORMATION DO WE COLLECT?\n" +
                "\n" +
                " \n" +
                "\n" +
                "Personal information you disclose to us\n" +
                "\n" +
                "In Short: We collect personal information that you provide to us such as name, address, contact information, passwords and security data, payment information, and social media login data.\n" +
                "\n" +
                "We collect personal information that you voluntarily provide to us when registering at the Sites or Apps, expressing an interest in obtaining information about us or our products and services, when participating in activities on the Sites or Apps (such as posting messages in our online forums or entering competitions, contests or giveaways) or otherwise contacting us.\n" +
                "\n" +
                "The personal information that we collect depends on the context of your interactions with us and the Sites or Apps, the choices you make and the products and features you use. The personal information we collect can include the following:\n" +
                "\n" +
                "Name and Contact Data. We collect your first and last name, email address, postal address, phone number, and other similar contact data.\n" +
                "\n" +
                "Credentials. We collect passwords, password hints, and similar security information used for authentication and account access.\n" +
                "\n" +
                "Payment Data. We collect data necessary to process your payment if you make purchases, such as your payment instrument number (such as a credit card number), and the security code associated with your payment instrument. All payment data is stored by our payment processor and you should review its privacy policies and contact the payment processor directly to respond to your questions.\n" +
                "\n" +
                "Social Media Login Data. We provide you with the option to register using social media account details, like your Facebook, Twitter or other social media account. If you choose to register in this way, we will collect the Information described in the section called \"HOW DO WE HANDLE YOUR SOCIAL LOGINS \" below.\n" +
                "\n" +
                "All personal information that you provide to us must be true, complete and accurate, and you must notify us of any changes to such personal information.\n" +
                "\n" +
                "Information automatically collected\n" +
                "\n" +
                "In Short: Some information – such as IP address and/or browser and device characteristics – is collected automatically when you visit our Sites or Apps.\n" +
                "\n" +
                "We automatically collect certain information when you visit, use or navigate the Sites or Apps. This information does not reveal your specific identity (like your name or contact information) but may include device and usage information, such as your IP address, browser and device characteristics, operating system, language preferences, referring URLs, device name, country, location, information about how and when you use our Sites or Apps and other technical information. This information is primarily needed to maintain the security and operation of our Sites or Apps, and for our internal analytics and reporting purposes.\n" +
                "\n" +
                "Information collected through our Apps\n" +
                "\n" +
                "In Short: We may collect information regarding your geo-location, push notifications, when you use our apps.\n" +
                "\n" +
                "If you use our Apps, we may also collect the following information:\n" +
                "\n" +
                "Geo-Location Information. We may request access or permission to and track location-based information from your mobile device, either continuously or while you are using our mobile application, to provide locationbased services. If you wish to change our access or permissions, you may do so in your device’s settings.\n" +
                "\n" +
                "Push Notifications. We may request to send you push notifications regarding your account or the mobile application. If you wish to opt-out from receiving these types of communications, you may turn them off in your device’s settings.\n" +
                "\n" +
                "Information collected from other sources\n" +
                "\n" +
                "In Short: We may collect limited data from public databases, marketing partners, social media platforms, and other outside sources.\n" +
                "\n" +
                "We may obtain information about you from other sources, such as public databases, joint marketing partners, social media platforms (such as Facebook), as well as from other third parties. Examples of the information we receive from other sources include: social media profile information (your name, gender, birthday, email, current city, state and country, user identification numbers for your contacts, profile picture URL and any other information that you choose to make public); marketing leads and search results and links, including paid listings (such as sponsored links).\n" +
                "\n" +
                " \n" +
                "\n" +
                "2. HOW DO WE USE YOUR INFORMATION?\n" +
                "\n" +
                "In Short: We process your information for purposes based on legitimate business interests, the fulfillment of our contract with you, compliance with our legal obligations, and/or your consent.\n" +
                "\n" +
                "We use personal information collected via our Sites or Apps for a variety of business purposes described below. We process your personal information for these purposes in reliance on our legitimate business interests, in order to enter into or perform a contract with you, with your consent, and/or for compliance with our legal obligations. We indicate the specific processing grounds we rely on next to each purpose listed below.\n" +
                "\n" +
                "We use the information we collect or receive:\n" +
                "\n" +
                " \n" +
                "\n" +
                "To send you marketing and promotional communications. We and/or our third party marketing partners may use the personal information you send to us for our marketing purposes, if this is in accordance with your marketing preferences. You can opt-out of our marketing emails at any time (see the \" WHAT ARE YOUR PRIVACY RIGHTS \" below).\n" +
                "\n" +
                " \n" +
                "\n" +
                "To send administrative information to you. We may use your personal information to send you product, service and new feature information and/or information about changes to our terms, conditions, and policies.\n" +
                "\n" +
                " \n" +
                "\n" +
                "To post testimonials. We post testimonials on our Sites or Apps that may contain personal information. Prior to posting a testimonial, we will obtain your consent to use your name and testimonial. If you wish to update, or delete your testimonial, please contact us at letsmeet@finlitdating.com and be sure to include your name, testimonial location, and contact information.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Deliver targeted advertising to you. We may use your information to develop and display content and advertising (and work with third parties who do so) tailored to your interests and/or location and to measure its effectiveness.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Request Feedback. We may use your information to request feedback and to contact you about your use of our\n" +
                "\n" +
                "Sites or Apps.\n" +
                "\n" +
                "To protect our Sites. We may use your information as part of our efforts to keep our Sites or Apps safe and secure (for example, for fraud monitoring and prevention).\n" +
                "\n" +
                "To enable user-to-user communications. We may use your information in order to enable user-to-user communications with each user's consent.\n" +
                "\n" +
                "To enforce our terms, conditions and policies.\n" +
                "\n" +
                "To respond to legal requests and prevent harm. If we receive a subpoena or other legal request, we may need to inspect the data we hold to determine how to respond.\n" +
                "\n" +
                "For other Business Purposes. We may use your information for other Business Purposes, such as data analysis, identifying usage trends, determining the effectiveness of our promotional campaigns and to evaluate and improve our Sites or Apps, products, services, marketing and your experience.\n" +
                "\n" +
                " \n" +
                "\n" +
                "3. WILL YOUR INFORMATION BE SHARED WITH ANYONE?\n" +
                "\n" +
                "In Short: We only share information with your consent, to comply with laws, to protect your rights, or to fulfill business obligations.\n" +
                "\n" +
                " \n" +
                "\n" +
                "We may process or share data based on the following legal basis:\n" +
                "\n" +
                "Consent: We may process your data if you have given us specific consent to use your personal information in a specific purpose.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Legitimate Interests: We may process your data when it is reasonably necessary to achieve our legitimate business interests.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Performance of a Contract: Where we have entered into a contract with you, we may process your personal information to fulfill the terms of our contract.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Legal Obligations: We may disclose your information where we are legally required to do so in order to comply with applicable law, governmental requests, a judicial proceeding, court order, or legal process, such as in response to a court order or a subpoena (including in response to public authorities to meet national security or law enforcement requirements).\n" +
                "\n" +
                " \n" +
                "\n" +
                "Vital Interests: We may disclose your information where we believe it is necessary to investigate, prevent, or take action regarding potential violations of our policies, suspected fraud, situations involving potential threats to the safety of any person and illegal activities, or as evidence in litigation in which we are involved.\n" +
                "\n" +
                "More specifically, we may need to process your data or share your personal information in the following situations:\n" +
                "\n" +
                "Vendors, Consultants and Other Third-Party Service Providers. We may share your data with third party vendors, service providers, contractors or agents who perform services for us or on our behalf and require access to such information to do that work. Examples include: payment processing, data analysis, email delivery, hosting services, customer service and marketing efforts. We may allow selected third parties to use tracking technology on the Sites or Apps, which will enable them to collect data about how you interact with the Sites or Apps over time. This information may be used to, among other things, analyze and track data, determine the popularity of certain content and better understand online activity. Unless described in this Policy, we do not share, sell, rent or trade any of your information with third parties for their promotional purposes.\n" +
                "\n" +
                "Business Transfers. We may share or transfer your information in connection with, or during negotiations of, any merger, sale of company assets, financing, or acquisition of all or a portion of our business to another company.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Business Partners. We may share your information with our business partners to offer you certain products, services or promotions.\n" +
                "\n" +
                " \n" +
                "\n" +
                "Other Users. When you share personal information (for example, by posting comments, contributions or other content to the Sites or Apps) or otherwise interact with public areas of the Sites or Apps, such personal information may be viewed by all users and may be publicly distributed outside the Sites or Apps in perpetuity. If you interact with other users of our Sites or Apps and register through a social network (such as Facebook), your contacts on the social network will see your name, profile photo, and descriptions of your activity.\n" +
                "\n" +
                "Similarly, other users will be able to view descriptions of your activity, communicate with you within our Sites or Apps, and view your profile.\n" +
                "\n" +
                " \n" +
                "\n" +
                "4. WHO WILL YOUR INFORMATION BE SHARED WITH?\n" +
                "\n" +
                "In Short: We only share information with the following third parties.\n" +
                "\n" +
                " \n" +
                "\n" +
                "We only share and disclose your information with the following third parties. We have categorized each party so that you may be easily understand the purpose of our data collection and processing practices. If we have processed your data based on your consent and you wish to revoke your consent, please contact us.\n" +
                "\n" +
                "Advertising, Direct Marketing, and Lead Generation Google AdSense and Facebook Audience Network\n" +
                "\n" +
                "Allow Users to Connect to their Third-Party Accounts\n" +
                "\n" +
                "Facebook account, Google account and LinkedIn account\n" +
                "\n" +
                "Content Optimization Google Site Search\n" +
                "\n" +
                "Invoice and Billing PayPal\n" +
                "\n" +
                " Retargeting Platforms\n" +
                "\n" +
                "Facebook Custom Audience, Google Ads Remarketing and Google Analytics Remarketing\n" +
                "\n" +
                "Social Media Sharing and Advertising\n" +
                "\n" +
                "Facebook advertising\n" +
                "\n" +
                "User Account Registration and Authentication\n" +
                "\n" +
                "Facebook Login, Google Sign-In and LinkedIn OAuth 2.0\n" +
                "\n" +
                "Web and Mobile Analytics\n" +
                "\n" +
                "Google Analytics\n" +
                "\n" +
                "Website Hosting WordPress.com\n" +
                "\n" +
                "Website Testing\n" +
                "\n" +
                "Google Play Console and TestFlight\n" +
                "\n" +
                " \n" +
                "\n" +
                "5.        HOW DO WE HANDLE YOUR SOCIAL LOGINS?\n" +
                "\n" +
                "In Short: If you choose to register or log in to our websites using a social media account, we may have access to certain information about you.\n" +
                "\n" +
                "Our Sites or Apps offer you the ability to register and login using your third party social media account details (like your Facebook or Twitter logins). Where you choose to do this, we will receive certain profile information about you from your social media provider. The profile Information we receive may vary depending on the social media provider concerned, but will often include your name, e-mail address, friends list, profile picture as well as other information you choose to make public.\n" +
                "\n" +
                "We will use the information we receive only for the purposes that are described in this privacy policy or that are otherwise made clear to you on the Sites or Apps. Please note that we do not control, and are not responsible for, other uses of your personal information by your third party social media provider. We recommend that you review their privacy policy to understand how they collect, use and share your personal information, and how you can set your privacy preferences on their sites and apps.\n" +
                "\n" +
                " \n" +
                "\n" +
                "6.        HOW LONG DO WE KEEP YOUR INFORMATION?\n" +
                "\n" +
                "In Short: We keep your information for as long as necessary to fulfill the purposes outlined in this privacy policy unless otherwise required by law.\n" +
                "\n" +
                "We will only keep your personal information for as long as it is necessary for the purposes set out in this privacy policy, unless a longer retention period is required or permitted by law (such as tax, accounting or other legal requirements). No purpose in this policy will require us keeping your personal information for longer than the period of time in which users have an account with us.\n" +
                "\n" +
                "When we have no ongoing legitimate business need to process your personal information, we will either delete or anonymize it, or, if this is not possible (for example, because your personal information has been stored in backup archives), then we will securely store your personal information and isolate it from any further processing until deletion is possible.\n" +
                "\n" +
                " \n" +
                "\n" +
                "7.        HOW DO WE KEEP YOUR INFORMATION SAFE?\n" +
                "\n" +
                "In Short: We aim to protect your personal information through a system of organizational and technical security measures.\n" +
                "\n" +
                "We have implemented appropriate technical and organizational security measures designed to protect the security of any personal information we process. However, please also remember that we cannot guarantee that the internet itself is 100% secure. Although we will do our best to protect your personal information, transmission of personal information to and from our Sites or Apps is at your own risk. You should only access the services within a secure environment.\n" +
                "\n" +
                " \n" +
                "\n" +
                "8.        DO WE COLLECT INFORMATION FROM MINORS?\n" +
                "\n" +
                "In Short: We do not knowingly collect data from or market to children under 18 years of age.\n" +
                "\n" +
                "We do not knowingly solicit data from or market to children under 18 years of age. By using the Sites or Apps, you represent that you are at least 18 or that you are the parent or guardian of such a minor and consent to such minor dependent’s use of the Sites or Apps. If we learn that personal information from users less than 18 years of age has been collected, we will deactivate the account and take reasonable measures to promptly delete such data from our records. If you become aware of any data we have collected from children under age 18, please contact us at letsmeet@finlitdating.com.\n" +
                "\n" +
                " \n" +
                "\n" +
                "9.        WHAT ARE YOUR PRIVACY RIGHTS?\n" +
                "\n" +
                "In Short: You may review, change, or terminate your account at any time.\n" +
                "\n" +
                "If you are resident in the European Economic Area and you believe we are unlawfully processing your personal information, you also have the right to complain to your local data protection supervisory authority. You can find their contact details here: http://ec.europa.eu/justice/data-protection/bodies/authorities/index_en.htm\n" +
                "\n" +
                " \n" +
                "\n" +
                "Account Information\n" +
                "\n" +
                "If you would at any time like to review or change the information in your account or terminate your account, you can: ■ Log into your account settings and update your user account.\n" +
                "\n" +
                "Upon your request to terminate your account, we will deactivate or delete your account and information from our active databases. However, some information may be retained in our files to prevent fraud, troubleshoot problems, assist with any investigations, enforce our Terms of Use and/or comply with legal requirements.\n" +
                "\n" +
                "Opting out of email marketing: You can unsubscribe from our marketing email list at any time by clicking on the unsubscribe link in the emails that we send or by contacting us using the details provided below. You will then be removed from the marketing email list – however, we will still need to send you service-related emails that are necessary for the administration and use of your account. To otherwise opt-out, you may:\n" +
                "\n" +
                "■ Access your account settings and update preferences.\n" +
                "\n" +
                " \n" +
                "\n" +
                "10. CONTROLS FOR DO-NOT-TRACK FEATURES\n" +
                "\n" +
                "Most web browsers and some mobile operating systems and mobile applications include a Do-Not-Track (“DNT”) feature or setting you can activate to signal your privacy preference not to have data about your online browsing activities monitored and collected. No uniform technology standard for recognizing and implementing DNT signals has been finalized. As such, we do not currently respond to DNT browser signals or any other mechanism that automatically communicates your choice not to be tracked online. If a standard for online tracking is adopted that we must follow in the future, we will inform you about that practice in a revised version of this Privacy Policy.\n" +
                "\n" +
                " \n" +
                "\n" +
                "11. DO CALIFORNIA RESIDENTS HAVE SPECIFIC PRIVACY RIGHTS?\n" +
                "\n" +
                "In Short: Yes, if you are a resident of California, you are granted specific rights regarding access to your personal information.\n" +
                "\n" +
                "California Civil Code Section 1798.83, also known as the “Shine The Light” law, permits our users who are California residents to request and obtain from us, once a year and free of charge, information about categories of personal information (if any) we disclosed to third parties for direct marketing purposes and the names and addresses of all third parties with which we shared personal information in the immediately preceding calendar year. If you are a California resident and would like to make such a request, please submit your request in writing to us using the contact information provided below.\n" +
                "\n" +
                "If you are under 18 years of age, reside in California, and have a registered account with the Sites or Apps, you have the right to request removal of unwanted data that you publicly post on the Sites or Apps. To request removal of such data, please contact us using the contact information provided below, and include the email address associated with your account and a statement that you reside in California. We will make sure the data is not publicly displayed on the Sites or Apps, but please be aware that the data may not be completely or comprehensively removed from our systems.\n" +
                "\n" +
                " \n" +
                "\n" +
                "12. DO WE MAKE UPDATES TO THIS POLICY?\n" +
                "\n" +
                "In Short: Yes, we will update this policy as necessary to stay compliant with relevant laws.\n" +
                "\n" +
                "We may update this privacy policy from time to time. The updated version will be indicated by an updated “Revised” date and the updated version will be effective as soon as it is accessible. If we make material changes to this privacy policy, we may notify you either by prominently posting a notice of such changes or by directly sending you a notification. We encourage you to review this privacy policy frequently to be informed of how we are protecting your information.\n" +
                "\n" +
                " \n" +
                "\n" +
                "13. HOW CAN YOU CONTACT US ABOUT THIS POLICY?\n" +
                "\n" +
                "If you have questions or comments about this policy, you may email us at letsmeet@finlitdating.com or by post to:\n" +
                "\n" +
                "Finlit Dating\n" +
                "501 Cowgill Avenue\n" +
                "Bellingham, WA 98225\n" +
                "United States");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        heading.setText("Privacy");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binder.unbind();
        binder = null;
    }
}
