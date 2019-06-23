#!/bin/sh

# Run this script to update all submodule repositories here
# to their latest version on their respective master branches.

git submodule update --recursive --remote &&

echo '
Updated all repositories to the latest commit on master.

You might find that "git status" reports something like:

   modified:   imagej-tutorials (new commits)

But that is OK! It means you have a newer version than what
was registered with this repository at the time. No worries.

You can also cd into any of the repositories here and
operate on them directly like normal git repositories.
'
