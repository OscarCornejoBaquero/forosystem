#!/bin/bash

# Create develop branch
git checkout -b develop
git push -u origin develop

# Create initial feature branches
features=(
    "payment-integration"
    "product-catalog"
    "user-authentication"
    "shopping-cart"
    "order-management"
)

for feature in "${features[@]}"
do
    # Create feature branch
    git flow feature start "$feature"
    
    # Push to remote
    git push -u origin "feature/$feature"
    
    # Return to develop
    git checkout develop
done

# Create initial release branch
git flow release start "1.0.0"
git push -u origin "release/1.0.0"