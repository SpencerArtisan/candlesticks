echo "Test run initiated..."
ls test/candlesticks/*_test.clj | xargs -n 1 clj -M:test -i

echo "Tests will be re-run as files change"

fswatch -o -i "clj$" -e ".*" . | xargs -n 1 sh -c 'echo "\nRunning tests..." && ls test/candlesticks/*_test.clj | xargs -n 1 clj -M:test -i'
