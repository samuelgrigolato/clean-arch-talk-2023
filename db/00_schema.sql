create table investors(
  id uuid not null primary key,
  email text not null
);

create table investments(
  id uuid not null primary key,
  amount decimal(10, 2) not null,
  investment_date date not null,
  redemption_date date,
  redeemed_amount decimal(10, 2),
  investor_id uuid not null,

  constraint fk_investments_investor foreign key (investor_id) references investors (id)
);

