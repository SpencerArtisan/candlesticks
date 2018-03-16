echo "Test run initiated..."
clj -C:test -i test/candlesticks/*.clj
echo "Tests will be re-run as files change"
fswatch -o -i "clj" -e ".*" . | xargs -n 1 sh -c 'echo "\nRunning tests..." && clj -C:test -i test/candlesticks/*.clj'
