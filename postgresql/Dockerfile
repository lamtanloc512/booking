FROM postgres:14

# Set environment variables
ENV POSTGRES_USER booking_user
ENV POSTGRES_PASSWORD booking_pwd
ENV POSTGRES_DB booking_db

# Copy the SQL script to initialize the database
#COPY init.sql /docker-entrypoint-initdb.d/init.sql

# Expose PostgreSQL port
EXPOSE 5432
