# GridImageSearch

An app for searching for images on google, with a few filter options

- User can enter a search query that will display a grid of image results from the Google Image API.
- User can click on "settings" which allows selection of advanced search options to filter results
- User can configure advanced search filters such as:
  - Size (small, medium, large, extra-large)
  - Color filter (black, blue, brown, gray, green, etc...)
  - Type (faces, photo, clip art, line art)
  - Site (espn.com)
- Subsequent searches will have any filters applied to the search results
- User can tap on any image in results to see the image full-screen
- User can scroll down “infinitely” to continue loading more image results (up to 8 pages)
- Checks if internet is available
- User can share an image to their friends or email it to themselves
- Used the StaggeredGridView to display improve the grid of image results

This took about 8 hours to write. 

See gif walkthrough at ![Video Walkthrough](https://s3.amazonaws.com/uploads.hipchat.com/20599/752135/4hUD1ilHtxuSTGS/image_search_demo.gif)

Gif of behavior when all results are filtered out: ![Video Walkthrough 2](https://s3.amazonaws.com/uploads.hipchat.com/20599/752135/FJ15wPieVgrTmdL/image_search_demo_no_results.gif)