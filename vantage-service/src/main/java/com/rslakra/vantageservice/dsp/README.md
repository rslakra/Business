# DSP

A ```Demand-Side Platform (DSP)``` database schema is complex, involving 
numerous components and relationships. Here is a simplified example focusing 
on core entities:

## Key Entities

- Advertisers

Companies or individuals purchasing ad inventory.

```text
-- Table: advertisers
CREATE TABLE advertisers (
    advertiser_id INT PRIMARY KEY AUTO_INCREMENT,
    advertiser_name VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255),
    -- Other relevant advertiser information
);
```


- Campaigns

Details of specific advertising campaigns, including targeting criteria, budget, and creatives.

```text
-- Table: campaigns
CREATE TABLE campaigns (
    campaign_id INT PRIMARY KEY AUTO_INCREMENT,
    advertiser_id INT,
    campaign_name VARCHAR(255) NOT NULL,
    start_date DATE,
    end_date DATE,
    budget DECIMAL(10, 2),
    -- Other campaign details (e.g., goals)
    FOREIGN KEY (advertiser_id) REFERENCES advertisers(advertiser_id)
);
```

- Line Items/Placements 

Smaller execution plans within a campaign, often with their own targeting, budget, and duration.

```text
-- Table: line_items
CREATE TABLE line_items (
    line_item_id INT PRIMARY KEY AUTO_INCREMENT,
    campaign_id INT,
    line_item_name VARCHAR(255),
    budget DECIMAL(10, 2),
    -- Targeting criteria details (e.g., geographical targeting, demographic data)
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);
```

- Creatives 

The actual ads (images, videos, etc.) that will be served.

```text
-- Table: creatives
CREATE TABLE creatives (
    creative_id INT PRIMARY KEY AUTO_INCREMENT,
    line_item_id INT,
    creative_url VARCHAR(255),
    creative_type VARCHAR(50),
    -- Other creative information
    FOREIGN KEY (line_item_id) REFERENCES line_items(line_item_id)
);
```

- Audience Segments 

Groups of users with shared characteristics used for targeting.

```text
-- Table: audience_segments
CREATE TABLE audience_segments (
    segment_id INT PRIMARY KEY AUTO_INCREMENT,
    segment_name VARCHAR(255),
    -- Details of the audience segment (e.g., demographics, interests)
);

```

- Users

Profiles of individual users, potentially with demographic, behavioral, and location data.

```text
-- Table: users
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    -- User profiling data (e.g., demographics, browsing history, location)
);
```

```text
-- Table: user_segments
CREATE TABLE user_segments (
    user_id INT,
    segment_id INT,
    PRIMARY KEY (user_id, segment_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (segment_id) REFERENCES audience_segments(segment_id)
);
```

- Impressions 

Records of each time an ad is displayed to a user.

```text
-- Table: impressions
CREATE TABLE impressions (
    impression_id INT PRIMARY KEY AUTO_INCREMENT,
    line_item_id INT,
    user_id INT,
    timestamp DATETIME,
    -- Other impression-related data (e.g., bid price, win price)
    FOREIGN KEY (line_item_id) REFERENCES line_items(line_item_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

- Clicks 

Records of clicks on ads.

```text
-- Table: clicks
CREATE TABLE clicks (
    click_id INT PRIMARY KEY AUTO_INCREMENT,
    impression_id INT,
    timestamp DATETIME,
    FOREIGN KEY (impression_id) REFERENCES impressions(impression_id)
);

```

- Conversions 

Records of desired actions taken by users after seeing an ad.

```text
-- Table: conversions
CREATE TABLE conversions (
    conversion_id INT PRIMARY KEY AUTO_INCREMENT,
    click_id INT,
    timestamp DATETIME,
    conversion_type VARCHAR(50), -- e.g., purchase, signup
    -- Other conversion-related data
    FOREIGN KEY (click_id) REFERENCES clicks(click_id)
);
```

- Bids 

Records of bids placed on ad impressions.

```text
-- Table: bids
CREATE TABLE bids (
    bid_id INT PRIMARY KEY AUTO_INCREMENT,
    impression_id INT, -- Refers to the impression that triggered the bid request
    line_item_id INT,
    user_id INT,
    bid_price DECIMAL(10, 2),
    timestamp DATETIME,
    -- Other bid-related data
    FOREIGN KEY (impression_id) REFERENCES impressions(impression_id),
    FOREIGN KEY (line_item_id) REFERENCES line_items(line_item_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

## Explanation and Considerations
This is a basic example. Real-world DSPs have significantly more tables and complex relationships, including integrations with SSPs, ad exchanges, and data management platforms.
Targeting information is included in line_items and user_segments. Advanced DSPs might have more granular targeting parameters.
For real-time bidding, DSPs require extremely low latency and high throughput, which impacts database choice. Specialized databases like Aerospike are often used.
The campaigns, impressions, clicks, and conversions tables are crucial for campaign reporting and analysis.
The users and user_segments tables support building user profiles for better targeting and optimization.
DSP databases handle massive amounts of data and require scalable architectures.


## Additional Information from Search Results
DSPs automate ad buying using real-time bidding (RTB).
They integrate with various data sources, including first-party, third-party, and contextual data.
DSP components include a bidder, ad server, data management system, campaign tracker, and budget manager.
DSPs use algorithms to optimize bidding and targeting.


## Tables

```shell
select * from addresses;
select * from audit_logs;
select * from content_taxonomies;
select * from documents;
select * from documents_versions;
select * from documents_permissions;
select * from marketing;
select * from permissions;
select * from roles;
select * from sessions;
select * from tasks;
select * from users;
select * from users_roles;
select * from user_security;
```

# Demand-Side Platform (DSP) - Component Requirements

## 1. Advertiser Interface (Frontend UI)
- Campaign creation/editing
- Creative upload (banner, video, native)
- Budget & schedule configuration
- Targeting UI (geo, device, time, interest, retargeting)
- Real-time reporting dashboard
- User roles and access control

## 2. Campaign Management System
- Validating campaign setup
- Storing campaign structure (ad groups, creatives, targeting rules)
- Scheduling and budget pacing
- Frequency and impression caps

## 3. Real-Time Bidding Engine (RTB Core)
- Evaluate bid requests
- Match to eligible campaigns
- Calculate optimal bid price
- Return bid response and creative ID

## 4. Targeting & Audience Matching
- Geo/IP, Device/browser, Behavioral (via DMP), Contextual, Retargeting
- Fast access via Redis, Aerospike
- Integration with DMP/CDP

## 5. Creative Management System
- Stores and delivers ad creatives
- Creative approval, format validation, rendering
- CDN integration (Cloudflare, Akamai)

## 6. Budgeting & Pacing Engine
- Manages budget constraints and pacing
- Bid throttling, even/performance pacing
- Real-time counters and rules engine

## 7. DMP Integration
- Lookalike modeling, audience segmentation, cookie/device mapping
- Precomputed audience tables

## 8. Analytics & Reporting
- Tracks performance metrics (CTR, CPA, eCPM)
- BI dashboard integration

## 9. Data Pipeline & Logging
- Captures all bid/impression/conversion events
- Kafka + schema registry, S3, ETL tools

## 10. Optimization & ML
- CTR prediction, Bid price optimization, Fraud detection
- Online model serving, Offline training

## 11. Fraud Detection & Viewability
- IP blacklisting, bot detection, JavaScript viewability tracking
- 3rd-party integrations (IAS, MOAT)

## 12. Security & Compliance
- GDPR / CCPA, encrypted data, audit logging, RBAC



enerating Revenue with a Demand-Side Platform (DSP)
Generating revenue with a Demand-Side Platform (DSP) involves multiple monetization strategies depending on your business model (B2B SaaS, performance-based, media-buying intermediary, or white-label DSP). Below are key revenue streams and business models used in the programmatic advertising ecosystem:

1. Media Markup (Margin-Based Model)
   You buy ad inventory from SSPs at $X and resell to advertisers at $X + margin.

Example:

Ad inventory cost: $1.00 CPM

You charge advertiser: $1.20 CPM

Profit = $0.20 CPM margin

Common margin: 10–30%

Automated inside bidding engine

2. SaaS Licensing (Flat-Fee or Tiered)
   Charge advertisers or agencies a monthly or annual platform fee for using your DSP dashboard and technology.

Tiered pricing based on:

Monthly ad spend

Number of campaigns/users

Features (DMP access, ML optimization, etc.)

3. % of Ad Spend (Usage-Based Pricing)
   Charge advertisers a percentage of their media spend through your platform.

Example: 10% fee on $50,000/month = $5,000 revenue

4. Data Monetization (Audience Segments)
   Sell targeting data or premium audiences for an additional fee.

Buy from or build your own DMP/CDP

Example: $0.50 CPM to target "Tech Enthusiasts"

5. Creative Services & Add-ons
   Offer creative production, optimization, or hosting as paid services.

Dynamic Creative Optimization (DCO)

A/B testing engines

Video ad production or interactive banners

6. White-Label DSP Offering
   Provide your DSP infrastructure to brands, agencies, or resellers under their own branding.

Charge a setup fee + monthly license

Offer optional managed services

7. Performance-Based Campaigns (CPA/CPL)
   Run performance marketing campaigns and take a cut of the revenue generated.

Cost-per-action (CPA), cost-per-lead (CPL), or ROAS-based

8. API Access for Developers/Partners
   Offer advanced users or partners access to APIs for integrations.

API pricing tiers

Rate limits based on plan

9. Training, Certification, and Consulting
   Monetize your expertise through:

Certification programs for marketers

In-house training for agency teams

Setup/onboarding consulting

Bonus: Combine Multiple Models
Most successful DSPs combine 3–4 of the above models for resilience and scaling.

# Reference

---

- [What is a Demand Side Platform (DSP) and How It Helps Publishers](https://www.publift.com/blog/what-is-a-demand-side-platform-dsp)
- [What Is a Demand-Side Platform (DSP) and How Does It Work?](https://clearcode.cc/blog/demand-side-platform/)
- 


# Author

---

- Rohtash Lakra
