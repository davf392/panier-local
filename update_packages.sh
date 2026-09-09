#!/bin/bash
# Script to update package declarations based on the file path

# Base directory for the files
BASE_DIR="/Users/davidfourdrigniez/AndroidStudioProjects/_perso/panier-local/composeApp/src/commonMain/kotlin/com/davf392/panierlocal"

# Loop through all Kotlin files
find "$BASE_DIR" -name "*.kt" | while read -r file; do
    # Get the relative path of the file from the base directory
    relative_path=${file#$BASE_DIR/}
    
    # Calculate the new package name: com.davf392.panierlocal + directory of the file
    # Get directory of the file
    dir=$(dirname "$relative_path")
    
    # If file is in the root of BASE_DIR, dir is "."
    if [ "$dir" == "." ]; then
        new_package="com.davf392.panierlocal"
    else
        # Replace slashes with dots
        new_package="com.davf392.panierlocal.${dir//\//.}"
    fi

    # Use sed to update the package declaration
    # This assumes the package is at the beginning of the line.
    sed -i '' "s/^package .*/package $new_package/" "$file"
    echo "Updated package for $file to $new_package"
done
